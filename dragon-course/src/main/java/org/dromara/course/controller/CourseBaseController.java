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
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.domain.bo.CourseBaseBo;
import org.dromara.course.service.CourseBaseService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程管理
 * 前端访问路由地址为:/course/CourseBase
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/CourseBase")
public class CourseBaseController extends BaseController {

    private final CourseBaseService courseBaseService;

    /**
     * 查询课程管理列表
     */
    @GetMapping("/list")
    public TableDataInfo<CourseBaseVo> list(CourseBaseBo bo, PageQuery pageQuery) {
        return courseBaseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程管理列表
     */
    @Log(title = "课程管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CourseBaseBo bo, HttpServletResponse response) {
        List<CourseBaseVo> list = courseBaseService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程管理", CourseBaseVo.class, response);
    }

    /**
     * 获取课程管理详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<CourseBaseVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable Long id) {
        return R.ok(courseBaseService.queryById(id));
    }

    /**
     * 新增课程管理
     */
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CourseBaseBo bo) {
        return toAjax(courseBaseService.insertByBo(bo));
    }

    /**
     * 修改课程管理
     */
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CourseBaseBo bo) {
        return toAjax(courseBaseService.updateByBo(bo));
    }

    /**
     * 删除课程管理
     *
     * @param ids 主键串
     */
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(courseBaseService.deleteWithValidByIds(List.of(ids), true));
    }
}
