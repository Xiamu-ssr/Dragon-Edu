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
import org.dromara.course.domain.vo.CourseCategoryVo;
import org.dromara.course.domain.bo.CourseCategoryBo;
import org.dromara.course.service.CourseCategoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 分类管理
 * 前端访问路由地址为:/course/category
 *
 * @author Xiamu
 * @date 2024-03-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CourseCategoryController extends BaseController {

    private final CourseCategoryService courseCategoryService;

    /**
     * 查询分类管理列表
     */
    @GetMapping("/list")
    public List<CourseCategoryVo> list(CourseCategoryBo bo) {
        return courseCategoryService.queryPageList(bo);
    }

    /**
     * 导出分类管理列表
     */
    @Log(title = "分类管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CourseCategoryBo bo, HttpServletResponse response) {
        List<CourseCategoryVo> list = courseCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "分类管理", CourseCategoryVo.class, response);
    }

    /**
     * 获取分类管理详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<CourseCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable String id) {
        return R.ok(courseCategoryService.queryById(id));
    }

    /**
     * 新增分类管理
     */
    @Log(title = "分类管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CourseCategoryBo bo) {
        return toAjax(courseCategoryService.insertByBo(bo));
    }

    /**
     * 修改分类管理
     */
    @Log(title = "分类管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CourseCategoryBo bo) {
        return toAjax(courseCategoryService.updateByBo(bo));
    }

    /**
     * 删除分类管理
     *
     * @param ids 主键串
     */
    @Log(title = "分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        return toAjax(courseCategoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
