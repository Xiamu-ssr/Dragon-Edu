package org.dromara.order.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.enums.UserType;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.order.domain.vo.OrderVo;
import org.dromara.order.domain.bo.OrderBo;
import org.dromara.order.service.OrderService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单服务
 * 前端访问路由地址为:/order/order
 *
 * @author LionLi
 * @date 2024-04-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final OrderService orderService;

    /**
     * 查询订单服务列表
     * <br/>
     * 用户用
     */
    @GetMapping("/list/user")
    public TableDataInfo<OrderVo> list(OrderBo bo, PageQuery pageQuery) {
        Long userId = LoginHelper.getUserId();
        bo.setUserId(userId);
        return orderService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询订单服务列表
     * <br/>
     * 机构用
     */
    @GetMapping("/list/company")
    public TableDataInfo<OrderVo> listCompany(OrderBo bo, PageQuery pageQuery) {
        Long deptId = LoginHelper.getDeptId();
        bo.setCompanyId(deptId);
        return orderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单服务列表
     */
    @Log(title = "订单服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(OrderBo bo, HttpServletResponse response) {
        List<OrderVo> list = orderService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单服务", OrderVo.class, response);
    }

    /**
     * 获取订单服务详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<OrderVo> getInfo(@NotNull(message = "主键不能为空")
                              @PathVariable Long id) {
        return R.ok(orderService.queryById(id));
    }

    /**
     * 新增订单服务
     */
    @Log(title = "订单服务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody OrderBo bo) {
        return toAjax(orderService.insertByBo(bo));
    }

    /**
     * 修改订单服务
     */
    @Log(title = "订单服务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody OrderBo bo) {
        return toAjax(orderService.updateByBo(bo));
    }

    /**
     * 删除订单服务
     *
     * @param ids 主键串
     */
    @Log(title = "订单服务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(orderService.deleteWithValidByIds(List.of(ids), true));
    }
}
