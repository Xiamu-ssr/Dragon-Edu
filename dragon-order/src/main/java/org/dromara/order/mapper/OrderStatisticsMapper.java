package org.dromara.order.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dromara.order.domain.OrderStatistics;
import org.dromara.order.domain.vo.OrderStatisticsVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.math.BigDecimal;

/**
 * 订单统计Mapper接口
 *
 * @author LionLi
 * @date 2024-05-05
 */
public interface OrderStatisticsMapper extends BaseMapperPlus<OrderStatistics, OrderStatisticsVo> {

    /**
     * 查询某机构某时间段销售数量
     *
     * @param companyId 公司id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return {@link Long}
     */
    @Select("SELECT SUM(sale_num) " +
        "FROM order_statistics " +
        "WHERE stat_date >= #{startDate} AND stat_date <= #{endDate} " +
        "AND company_id = #{companyId}")
    Long queryRangeSaleNum(@Param("companyId") Long companyId,
                              @Param("startDate") String startDate,
                              @Param("endDate") String endDate
    );

    /**
     * 查询某机构某时间段销售额度
     *
     * @param companyId 公司id
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return {@link Long}
     */
    @Select("SELECT SUM(sale) " +
        "FROM order_statistics " +
        "WHERE stat_date >= #{startDate} AND stat_date <= #{endDate} " +
        "AND company_id = #{companyId}")
    BigDecimal queryRangeSale(@Param("companyId") Long companyId,
                              @Param("startDate") String startDate,
                              @Param("endDate") String endDate
    );

}
