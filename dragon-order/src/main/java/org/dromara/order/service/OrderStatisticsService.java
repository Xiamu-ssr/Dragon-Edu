package org.dromara.order.service;

import org.dromara.order.domain.OrderStatistics;

import java.time.LocalDate;
import java.util.Map;

public interface OrderStatisticsService {

    /**
     * 查询范围
     *
     * @param companyId 公司id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return {@link Map}<{@link LocalDate}, {@link OrderStatistics}>
     */
    Map<LocalDate, OrderStatistics> queryRange(Long companyId, LocalDate startDate, LocalDate endDate);
}
