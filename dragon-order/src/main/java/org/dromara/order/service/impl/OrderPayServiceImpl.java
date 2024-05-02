package org.dromara.order.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.mq.domain.OrderMessage;
import org.dromara.common.qrcode.utils.QRCodeUtil;
import org.dromara.course.api.RemoteCourseService;
import org.dromara.course.api.domain.CourseBase;
import org.dromara.order.config.AlipayConfig;
import org.dromara.order.domain.Order;
import org.dromara.order.domain.PayStatusDto;
import org.dromara.order.domain.vo.OrderVo;
import org.dromara.order.domain.vo.PayQRCodeVo;
import org.dromara.order.enums.OrderStatusEnum;
import org.dromara.order.mapper.OrderMapper;
import org.dromara.order.mq.producer.OrderStreamProducer;
import org.dromara.order.service.OrderPayService;
import org.dromara.order.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class OrderPayServiceImpl implements OrderPayService {

    @Value("${pay.qrcodeurl}")
    private String qrcodeurl;
    @Value("${pay.alipay.APP_ID}")
    String APP_ID;
    @Value("${pay.alipay.APP_PRIVATE_KEY}")
    String APP_PRIVATE_KEY;
    @Value("${pay.alipay.ALIPAY_PUBLIC_KEY}")
    String ALIPAY_PUBLIC_KEY;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    @Lazy
    OrderPayService payService;
    @Autowired
    OrderService orderService;

    @Autowired
    OrderStreamProducer orderStreamProducer;

    @DubboReference
    RemoteCourseService remoteCourseService;

    @Override
    public PayQRCodeVo generatePayCode(Long userId, Long courseId) {
        //创建订单
        CourseBase courseBase = remoteCourseService.getCourseBaseById(courseId);
        Order order = new Order();
        order.setCourseId(courseId);
        order.setCourseName(courseBase.getName());
        order.setCompanyId(courseBase.getCompanyId());
        order.setUserId(userId);
        order.setPrice(courseBase.getPrice());
        order.setStatus(OrderStatusEnum.UNPAID.getValue());
        boolean b1 = orderMapper.insert(order) > 0;
        //生成二维码
        Long payNo = order.getId();
        String url = String.format(qrcodeurl, payNo);
        String qrCode = null;
        try {
            qrCode = QRCodeUtil.createQRCode(url, 200, 200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //return
        PayQRCodeVo payQRCodeVo = new PayQRCodeVo();
        payQRCodeVo.setPayNo(payNo);
        payQRCodeVo.setQrcode(qrCode);
        return payQRCodeVo;
    }

    @Override
    public boolean queryPayResult(Long payNo) {
        PayStatusDto payStatusDto = queryPayResultFromAlipay(payNo);
        if (payStatusDto == null){
            throw new BaseException("查询订单支付状态失败");
        }
        return payService.saveAliPayStatus(payStatusDto);
    }

    /**
     * 从支付宝查询支付结果
     *
     * @param payNo payNo
     * @return {@link PayStatusDto}
     */
    private PayStatusDto queryPayResultFromAlipay(Long payNo){
        //查询支付状态
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, APP_ID, APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        //创建API对应的request
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", payNo);
        alipayRequest.setBizContent(bizContent.toString());//填充业务参数
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            return null;
        }
        //解析支付结果
        PayStatusDto payStatusDto = new PayStatusDto();
        payStatusDto.setOut_trade_no(payNo);
        payStatusDto.setTrade_no(response.getTradeNo());
        payStatusDto.setTrade_status(response.getTradeStatus());
        payStatusDto.setApp_id(APP_ID);
        payStatusDto.setTotal_amount(response.getTotalAmount());

        return payStatusDto;
    }


    /**
     * 保存支付状态
     *
     * @param payStatusDto 支付状态dto
     */
    @Override
    @Transactional
    public boolean saveAliPayStatus(PayStatusDto payStatusDto){
        //约束
        Long payNo = payStatusDto.getOut_trade_no();
        OrderVo orderVo = orderService.queryById(payNo);
        if (orderVo == null){
            throw new BaseException("查询不到支付记录");
        }
        //判断是否已经设置过支付成功
        if (OrderStatusEnum.PAID_SUCCESS.getValue()== orderVo.getStatus()){
            return true;
        }
        if ("TRADE_SUCCESS".equals(payStatusDto.getTrade_status())){
            //更新订单表状态order
            LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<Order>()
                .eq(Order::getId, payNo)
                .set(Order::getStatus, OrderStatusEnum.PAID_SUCCESS.getValue());
            boolean b1 = orderMapper.update(updateWrapper) > 0;
            //发送支付成功的消息到rabbitMq
            OrderMessage order = new OrderMessage();
            BeanUtils.copyProperties(orderVo, order);
            orderStreamProducer.orderSupplier(order);
            return true;
        }else {
            //更新订单表状态order
            LambdaUpdateWrapper<Order> updateWrapper = new LambdaUpdateWrapper<Order>()
                .eq(Order::getId, payNo)
                .set(Order::getStatus, OrderStatusEnum.PAID_FAILED.getValue());
            boolean b1 = orderMapper.update(updateWrapper) > 0;
            throw new BaseException("订单-未支付成功");
        }
    }
}
