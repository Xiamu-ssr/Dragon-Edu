package org.dromara.order.service.impl;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.core.constant.CacheNames;
import org.dromara.common.core.domain.R;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.course.api.RemoteCourseService;
import org.dromara.discuss.api.RemoteDiscussService;
import org.dromara.discuss.api.domain.BestCourseDto;
import org.dromara.order.domain.Order;
import org.dromara.order.domain.OrderStatistics;
import org.dromara.order.domain.SaleData;
import org.dromara.order.domain.vo.BestCourseVo;
import org.dromara.order.domain.vo.CurrentOrdersVo;
import org.dromara.order.domain.vo.SaleDataEchartsVo;
import org.dromara.order.domain.vo.TotalStatisticsVo;
import org.dromara.order.enums.OrderStatusEnum;
import org.dromara.order.mapper.OrderMapper;
import org.dromara.order.mapper.SaleDataMapper;
import org.dromara.order.service.CompanyStatisticsService;
import org.dromara.order.service.OrderStatisticsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CompanyStatisticsServiceImpl implements CompanyStatisticsService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    SaleDataMapper saleDataMapper;

    @Autowired
    OrderStatisticsService orderStatisticsService;


    @DubboReference
    RemoteCourseService remoteCourseService;
    @DubboReference
    RemoteDiscussService remoteDiscussService;

    public TotalStatisticsVo getTotalData(Long companyId){
        TotalStatisticsVo vo = new TotalStatisticsVo();
        //fill data
        SaleData saleData = saleDataMapper.selectOne(new LambdaQueryWrapper<SaleData>()
            .eq(SaleData::getCompanyId, companyId));
        if (saleData != null){
            vo.setOrderNum(saleData.getSaleNum());
            vo.setRevenue(saleData.getRevenue());
        }else {
            vo.setOrderNum(0L);
            vo.setRevenue(BigDecimal.ZERO);
        }
        //orderThisWeek
        vo.setOrderThisWeek(orderStatisticsService.queryThisWeekSaleNum(companyId));
        //revenueGrowRate
        BigDecimal lastWeekSale = orderStatisticsService.queryLastWeekSale(companyId);
        BigDecimal last2WeekSale = orderStatisticsService.queryLastXWeekSale(companyId, 2);
        if (Objects.equals(last2WeekSale, BigDecimal.ZERO)){
            vo.setRevenueGrowRate(1000);
        }else {
            BigDecimal salesIncrement = lastWeekSale.subtract(last2WeekSale);
            BigDecimal growthRate = salesIncrement.divide(last2WeekSale, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            vo.setRevenueGrowRate(growthRate.intValue());
        }
        //courseNum
        //teachplanNum
        List<Integer> tmp = remoteCourseService.getCourseNumAndTeachplanNum(companyId);
        vo.setCourseNum(tmp.get(0));
        vo.setTeachplanNum(tmp.get(1));
        //discussNum
        vo.setDiscussNum(remoteDiscussService.getDiscussNum(companyId));
        //discussAvg
        if (vo.getCourseNum() != 0) {
            Integer result = vo.getDiscussNum() / vo.getCourseNum();
            vo.setDiscussAvg(result);
        } else {
            vo.setDiscussAvg(0);
        }
        //currentOrders
        ArrayList<CurrentOrdersVo> currentOrdersVos = new ArrayList<>();
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>()
            .select(Order::getCourseId, Order::getCourseName, Order::getPrice)
            .eq(Order::getCompanyId, companyId)
            .eq(Order::getStatus, OrderStatusEnum.PAID_SUCCESS.getValue())
            .orderByDesc(Order::getCreateTime)
            .last("limit 10");
        List<Order> orders = orderMapper.selectList(queryWrapper);
        List<Long> ids = orders.stream().map(Order::getId).toList();
        Map<Long, String> id2pic = remoteCourseService.getCoursePics(ids);
        for (Order order : orders) {
            CurrentOrdersVo vo1 = new CurrentOrdersVo();
            BeanUtils.copyProperties(order, vo1);
            vo1.setPic(id2pic.get(vo1.getCourseId()));
            currentOrdersVos.add(vo1);
        }
        vo.setCurrentOrders(currentOrdersVos);
        //saleData
        LocalDate endDate = LocalDate.now().minusDays(1); // 昨天
        LocalDate startDate = LocalDate.now().minusDays(14); // 前14天
        Map<LocalDate, OrderStatistics> map = orderStatisticsService.queryRange(companyId, startDate, endDate);
        ArrayList<String> xAxis = new ArrayList<>();
        ArrayList<Integer> saleNum = new ArrayList<>();
        ArrayList<BigDecimal> saleMoney = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            xAxis.add(date.toString());
            saleNum.add(map.get(date).getSaleNum().intValue());
            saleMoney.add(map.get(date).getSale());
        }
        SaleDataEchartsVo echartsVo = new SaleDataEchartsVo();
        echartsVo.setXAxis(xAxis);
        echartsVo.setSaleNum(saleNum);
        echartsVo.setSaleMoney(saleMoney);
        vo.setSaleData(echartsVo);
        //bestCourse
        List<BestCourseDto> bestList = remoteDiscussService.getBestCourse(companyId);
        List<BestCourseVo> bestCourseVos = bestList.stream().map(bestCourseDto -> {
            BestCourseVo bestCourseVo = new BestCourseVo();
            BeanUtils.copyProperties(bestCourseDto, bestCourseVo);
            return bestCourseVo;
        }).toList();
        vo.setBestCourse(bestCourseVos);
        return vo;
    }

    @Override
    public TotalStatisticsVo getTotalDataCache(Long companyId) {
        String key = CacheNames.COMPANY_STATISTICS+":"+companyId.toString();
        String cachedValue = RedisUtils.getCacheObject(key);
        if (cachedValue == null){
            TotalStatisticsVo totalData = getTotalData(companyId);
            RedisUtils.setCacheObject(key, JSON.toJSON(totalData));
            RedisUtils.expire(key, Duration.ofHours(1).getSeconds());
            return totalData;
        }else {
            return JSON.parseObject(cachedValue, TotalStatisticsVo.class);
        }
    }
}
