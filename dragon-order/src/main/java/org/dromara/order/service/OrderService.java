package org.dromara.order.service;

import org.dromara.order.domain.Order;
import org.dromara.order.domain.vo.OrderVo;
import org.dromara.order.domain.bo.OrderBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单服务Service接口
 *
 * @author LionLi
 * @date 2024-04-27
 */
public interface OrderService {

    /**
     * 查询订单服务
     */
    OrderVo queryById(Long id);

    /**
     * 查询订单服务列表
     */
    TableDataInfo<OrderVo> queryPageList(OrderBo bo, PageQuery pageQuery);

    /**
     * 查询订单服务列表
     */
    List<OrderVo> queryList(OrderBo bo);

    /**
     * 新增订单服务
     */
    Boolean insertByBo(OrderBo bo);

    /**
     * 修改订单服务
     */
    Boolean updateByBo(OrderBo bo);

    /**
     * 校验并批量删除订单服务信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
