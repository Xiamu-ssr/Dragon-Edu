package org.dromara.discuss.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
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
import org.dromara.discuss.domain.vo.DiscussVo;
import org.dromara.discuss.domain.bo.DiscussBo;
import org.dromara.discuss.service.DiscussService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程评论
 * 前端访问路由地址为:/discuss/discuss
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/discuss")
public class DiscussController extends BaseController {

    private final DiscussService discussService;

    /**
     * 查询课程评论列表
     */
    @GetMapping("/list")
    public TableDataInfo<DiscussVo> list(DiscussBo bo, PageQuery pageQuery) {
        return discussService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程评论列表
     */
    @Log(title = "课程评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(DiscussBo bo, HttpServletResponse response) {
        List<DiscussVo> list = discussService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程评论", DiscussVo.class, response);
    }

    /**
     * 获取课程评论详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<DiscussVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long id) {
        return R.ok(discussService.queryById(id));
    }

    /**
     * 新增课程评论
     */
    @Log(title = "课程评论", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DiscussBo bo) {
        return toAjax(discussService.insertByBo(bo));
    }

    /**
     * 修改课程评论
     */
    @Log(title = "课程评论", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DiscussBo bo) {
        return toAjax(discussService.updateByBo(bo));
    }

    /**
     * 删除课程评论
     *
     * @param ids 主键串
     */
    @Log(title = "课程评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(discussService.deleteWithValidByIds(List.of(ids), true));
    }
}
