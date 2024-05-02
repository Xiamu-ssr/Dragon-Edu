package org.dromara.order.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.order.domain.bo.OrderBo;
import org.dromara.order.domain.vo.OrderVo;
import org.dromara.order.domain.Order;
import org.dromara.order.mapper.OrderMapper;
import org.dromara.order.service.OrderService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单服务Service业务层处理
 *
 * @author LionLi
 * @date 2024-04-27
 */
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper baseMapper;

    /**
     * 查询订单服务
     */
    @Override
    public OrderVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询订单服务列表
     */
    @Override
    public TableDataInfo<OrderVo> queryPageList(OrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Order> lqw = buildQueryWrapper(bo);
        Page<OrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单服务列表
     */
    @Override
    public List<OrderVo> queryList(OrderBo bo) {
        LambdaQueryWrapper<Order> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Order> buildQueryWrapper(OrderBo bo) {
        LambdaQueryWrapper<Order> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCourseId() != null, Order::getCourseId, bo.getCourseId());
        lqw.like(StringUtils.isNotBlank(bo.getCourseName()), Order::getCourseName, bo.getCourseName());
        lqw.eq(bo.getCompanyId() != null, Order::getCompanyId, bo.getCompanyId());
        lqw.eq(bo.getUserId() != null, Order::getUserId, bo.getUserId());
        lqw.eq(bo.getPrice() != null, Order::getPrice, bo.getPrice());
        lqw.eq(bo.getStatus() != null, Order::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), Order::getRemarks, bo.getRemarks());
        lqw.orderByDesc(Order::getId);
        return lqw;
    }

    /**
     * 新增订单服务
     */
    @Override
    public Boolean insertByBo(OrderBo bo) {
        Order add = MapstructUtils.convert(bo, Order.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改订单服务
     */
    @Override
    public Boolean updateByBo(OrderBo bo) {
        Order update = MapstructUtils.convert(bo, Order.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Order entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除订单服务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
