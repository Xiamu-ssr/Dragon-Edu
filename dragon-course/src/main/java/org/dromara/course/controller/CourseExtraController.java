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
import org.dromara.course.domain.vo.CourseExtraVo;
import org.dromara.course.domain.bo.CourseExtraBo;
import org.dromara.course.service.CourseExtraService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程额外信息
 * 前端访问路由地址为:/course/CourseExtra
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/CourseExtra")
public class CourseExtraController extends BaseController {

    private final CourseExtraService courseExtraService;

    /**
     * 查询课程额外信息列表
     */
    @GetMapping("/list")
    public TableDataInfo<CourseExtraVo> list(CourseExtraBo bo, PageQuery pageQuery) {
        return courseExtraService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程额外信息列表
     */
    @Log(title = "课程额外信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CourseExtraBo bo, HttpServletResponse response) {
        List<CourseExtraVo> list = courseExtraService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程额外信息", CourseExtraVo.class, response);
    }

    /**
     * 获取课程额外信息详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<CourseExtraVo> getInfo(@NotNull(message = "主键不能为空")
                                    @PathVariable Long id) {
        return R.ok(courseExtraService.queryById(id));
    }

    /**
     * 新增课程额外信息
     */
    @Log(title = "课程额外信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CourseExtraBo bo) {
        return toAjax(courseExtraService.insertByBo(bo));
    }

    /**
     * 修改课程额外信息
     */
    @Log(title = "课程额外信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CourseExtraBo bo) {
        return toAjax(courseExtraService.updateByBo(bo));
    }

    /**
     * 删除课程额外信息
     *
     * @param ids 主键串
     */
    @Log(title = "课程额外信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(courseExtraService.deleteWithValidByIds(List.of(ids), true));
    }
}
