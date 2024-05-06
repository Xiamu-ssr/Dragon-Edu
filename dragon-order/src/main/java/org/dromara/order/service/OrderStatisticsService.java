package org.dromara.order.service;

import org.dromara.order.domain.OrderStatistics;

import java.math.BigDecimal;
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

    /**
     * 查询某机构本周销售数量
     *
     * @param companyId 公司id
     * @return {@link Long}
     */
    Long queryThisWeekSaleNum(Long companyId);

    /**
     * 查询某机构上周销售数量
     *
     * @param companyId 公司id
     * @return {@link Long}
     */
    Long queryLastWeekSaleNum(Long companyId);

    /**
     * 查询某机构上X周销售数量
     *
     * @param companyId 公司id
     * @return {@link Long}
     */
    Long queryLastXWeekSaleNum(Long companyId, int X);

    /**
     * 查询某机构本周销售额度
     *
     * @param companyId 公司id
     * @return {@link Long}
     */
    BigDecimal queryThisWeekSale(Long companyId);

    /**
     * 查询某机构上周销售额度
     *
     * @param companyId 公司id
     * @return {@link Long}
     */
    BigDecimal queryLastWeekSale(Long companyId);


    /**
     * 查询某机构上X周销售额度
     *
     * @param companyId 公司id
     * @return {@link Long}
     */
    BigDecimal queryLastXWeekSale(Long companyId, int X);

}
