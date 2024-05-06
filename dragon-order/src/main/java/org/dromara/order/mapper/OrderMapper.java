package org.dromara.order.mapper;

import org.apache.ibatis.annotations.Select;
import org.dromara.order.domain.CompanySalesDTO;
import org.dromara.order.domain.Order;
import org.dromara.order.domain.vo.OrderVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 订单服务Mapper接口
 *
 * @author LionLi
 * @date 2024-04-27
 */
public interface OrderMapper extends BaseMapperPlus<Order, OrderVo> {

    /**
     * 为每个company查询前一天的订单统计信息
     *
     * @return {@link List}<{@link CompanySalesDTO}>
     */
    @Select("SELECT company_id, COUNT(*) AS order_count, SUM(price) AS total_sales " +
        "FROM order_table WHERE DATE(create_time) = CURDATE() - INTERVAL 1 DAY " +
        "AND status = 2 "+
        "GROUP BY company_id")
    List<CompanySalesDTO> queryStatistics();
}
