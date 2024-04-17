package org.dromara.course.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.api.model.LoginUser;
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
import org.dromara.course.domain.vo.TeacherVo;
import org.dromara.course.domain.bo.TeacherBo;
import org.dromara.course.service.TeacherService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 教师管理
 * 前端访问路由地址为:/course/Teacher
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/Teacher")
public class TeacherController extends BaseController {

    private final TeacherService teacherService;

    /**
     * 查询教师管理列表
     */
    @GetMapping("/list")
    public TableDataInfo<TeacherVo> list(TeacherBo bo, PageQuery pageQuery) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        bo.setCompanyId(loginUser.getDeptId());
        return teacherService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出教师管理列表
     */
    @Log(title = "教师管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(TeacherBo bo, HttpServletResponse response) {
        List<TeacherVo> list = teacherService.queryList(bo);
        ExcelUtil.exportExcel(list, "教师管理", TeacherVo.class, response);
    }

    /**
     * 获取教师管理详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<TeacherVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String id) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        return R.ok(teacherService.queryById(id, loginUser.getDeptId()));
    }

    /**
     * 新增教师管理
     */
    @Log(title = "教师管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TeacherBo bo) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        bo.setCompanyId(loginUser.getDeptId());
        return toAjax(teacherService.insertByBo(bo));
    }

    /**
     * 修改教师管理
     */
    @Log(title = "教师管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TeacherBo bo) {
        return toAjax(teacherService.updateByBo(bo));
    }

    /**
     * 删除教师管理
     *
     * @param ids 主键串
     */
    @Log(title = "教师管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        return toAjax(teacherService.deleteWithValidByIds(List.of(ids), true));
    }
}
