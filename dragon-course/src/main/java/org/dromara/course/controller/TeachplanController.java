package org.dromara.course.controller;

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
import org.dromara.course.domain.vo.TeachplanVo;
import org.dromara.course.domain.bo.TeachplanBo;
import org.dromara.course.service.TeachplanService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程计划
 * 前端访问路由地址为:/course/Teachplan
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/Teachplan")
public class TeachplanController extends BaseController {

    private final TeachplanService teachplanService;

    /**
     * 查询课程计划列表
     */
    @GetMapping("/list")
    public TableDataInfo<TeachplanVo> list(TeachplanBo bo, PageQuery pageQuery) {
        return teachplanService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程计划列表
     */
    @Log(title = "课程计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TeachplanBo bo, HttpServletResponse response) {
        List<TeachplanVo> list = teachplanService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程计划", TeachplanVo.class, response);
    }

    /**
     * 获取课程计划详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<TeachplanVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String id) {
        return R.ok(teachplanService.queryById(id));
    }

    /**
     * 新增课程计划
     */
    @Log(title = "课程计划", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TeachplanBo bo) {
        return toAjax(teachplanService.insertByBo(bo));
    }

    /**
     * 修改课程计划
     */
    @Log(title = "课程计划", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TeachplanBo bo) {
        return toAjax(teachplanService.updateByBo(bo));
    }

    /**
     * 删除课程计划
     *
     * @param ids 主键串
     */
    @Log(title = "课程计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        return toAjax(teachplanService.deleteWithValidByIds(List.of(ids), true));
    }
}
