package org.dromara.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.order.domain.OrderStatistics;
import org.dromara.order.mapper.OrderStatisticsMapper;
import org.dromara.order.service.OrderStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {
    @Autowired
    OrderStatisticsMapper orderStatisticsMapper;

    @Override
    public Map<LocalDate, OrderStatistics> queryRange(Long companyId, LocalDate startDate, LocalDate endDate) {
        //query data
        LambdaQueryWrapper<OrderStatistics> queryWrapper = new LambdaQueryWrapper<OrderStatistics>()
            .eq(OrderStatistics::getCompanyId, companyId)
            .ge(OrderStatistics::getStatDate, startDate)
            .le(OrderStatistics::getStatDate, endDate)
            .orderByAsc(OrderStatistics::getStatDate);
        List<OrderStatistics> orderStatistics = orderStatisticsMapper.selectList(queryWrapper);
        //transform to Map
        Map<LocalDate, OrderStatistics> dateMap = orderStatistics.stream().collect(Collectors.toMap(OrderStatistics::getStatDate, Function.identity()));
        //fill date which has no data
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dateMap.putIfAbsent(date, new OrderStatistics(null,companyId, 0L, BigDecimal.ZERO, date));
        }
        return dateMap;
    }
}
