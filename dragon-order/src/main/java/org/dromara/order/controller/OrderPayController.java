package org.dromara.order.controller;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dromara.common.core.domain.R;
import org.dromara.common.mq.domain.OrderMessage;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.order.config.AlipayConfig;
import org.dromara.order.domain.PayStatusDto;
import org.dromara.order.domain.vo.OrderVo;
import org.dromara.order.domain.vo.PayQRCodeVo;
import org.dromara.order.enums.OrderStatusEnum;
import org.dromara.order.mq.producer.OrderStreamProducer;
import org.dromara.order.service.OrderPayService;
import org.dromara.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/orderPay")
public class OrderPayController {

    @Value("${pay.alipay.APP_ID}")
    String APP_ID;
    @Value("${pay.alipay.APP_PRIVATE_KEY}")
    String APP_PRIVATE_KEY;
    @Value("${pay.alipay.ALIPAY_PUBLIC_KEY}")
    String ALIPAY_PUBLIC_KEY;

    @Autowired
    OrderPayService orderPayService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderStreamProducer orderProducer;

    /**
     * 生成付款二维码
     * <br/>
     * 不开放，需登录
     *
     * @param courseId 课程id
     * @return {@link R}<{@link PayQRCodeVo}>
     */
    @GetMapping("/generatePayCode")
    @ResponseBody
    public R<PayQRCodeVo> generatePayCode(Long courseId){
        Long userId = LoginHelper.getUserId();
        PayQRCodeVo payRecordVo = orderPayService.generatePayCode(userId, courseId);
        if (payRecordVo != null){
            return R.ok(payRecordVo);
        }else {
            return R.fail("获取二维码失败，请确认是否登陆.");
        }
    }

    /**
     * requestpay
     *
     * @param payNo        订单号
     * @param httpResponse http响应
     * @throws AlipayApiException 支付宝api异常
     * @throws IOException        IOException
     */
    @RequestMapping("/requestPay")
    public R<Void> requestPay(Long payNo, HttpServletResponse httpResponse) throws AlipayApiException, IOException {
        //约束
        OrderVo orderVo = orderService.queryById(payNo);
        if (orderVo == null){
            return R.fail("订单记录不存在!");
        }
        if (OrderStatusEnum.PAID_SUCCESS.getValue() == orderVo.getStatus()){
            return R.ok("已支付，无需重复支付");
        }

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, APP_ID, APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        //在公共参数中设置回跳和通知地址
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        //alipayRequest.setReturnUrl(AlipayConfig.return_url);
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", payNo);
        //支付金额，最小值0.01元
        bizContent.put("total_amount", orderVo.getPrice());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", orderVo.getCourseName());
        bizContent.put("product_code", "QUICK_WAP_WAY");
        alipayRequest.setBizContent(bizContent.toString());//填充业务参数

        String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        httpResponse.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        return R.ok("请求支付成功");
    }

    /**
     * 主动查询订单支付结果
     *
     * @param payNo 不付款
     * @return {@link R}<{@link Boolean}>
     */
    @GetMapping("/payResult")
    @ResponseBody
    public R<Boolean> payResult(Long payNo) {
        boolean b = orderPayService.queryPayResult(payNo);
        return R.ok(b);
    }


    /**
     * 被动（异步）接收支付宝支持结果通知
     *
     * @param request  要求
     * @param response 回答
     * @throws AlipayApiException 支付宝api异常
     * @throws IOException        IOException
     */
    @PostMapping("/receiveNotify")
    public void receiveNotify(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                    : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

        if(verify_result) {//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            //交易金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            PayStatusDto payStatusDto = new PayStatusDto();
            payStatusDto.setOut_trade_no(Long.parseLong(out_trade_no));
            payStatusDto.setTrade_no(trade_no);
            payStatusDto.setTrade_status(trade_status);
            //app_id和total_amount可以不填
            payStatusDto.setApp_id(APP_ID);
            payStatusDto.setTotal_amount(total_amount);
            orderPayService.saveAliPayStatus(payStatusDto);
            response.getWriter().write("success");
        }else{
            response.getWriter().write("支付情况校验失败");
        }
    }


}
