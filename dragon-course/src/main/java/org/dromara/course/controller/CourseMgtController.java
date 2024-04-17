package org.dromara.course.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.web.core.BaseController;
import org.dromara.course.domain.Teacher;
import org.dromara.course.domain.bo.*;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.domain.vo.CourseMgt1Vo;
import org.dromara.course.domain.vo.CourseMgt2Vo;
import org.dromara.course.service.CourseBaseService;
import org.dromara.course.service.CourseMgtService;
import org.dromara.system.api.model.LoginUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程
 * 前端访问路由地址为:/course/base
 *
 * @author LionLi
 * @date 2024-03-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/mgt")
public class CourseMgtController extends BaseController {

    private final CourseMgtService courseMgtService;

    /**
     * 查询课程列表
     */
    @GetMapping("/list")
    public TableDataInfo<CourseBaseVo> list(CourseBaseBo bo, PageQuery pageQuery) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        bo.setCompanyId(loginUser.getDeptId());
        return courseMgtService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程列表
     */
    @Log(title = "课程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CourseBaseBo bo, HttpServletResponse response) {
        List<CourseBaseVo> list = courseMgtService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程", CourseBaseVo.class, response);
    }

    /**
     * 获取课程详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<CourseBaseVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(courseMgtService.queryById(id));
    }

    /**
     * 获取课程-第一阶段信息
     *
     * @param id 主键
     */
    @GetMapping("/one")
    public R<CourseMgt1Vo> getOne(@RequestParam Long id) {
        return R.ok(courseMgtService.getOne(id));
    }


    /**
     * 获取课程-第二阶段信息
     *
     * @param id 主键
     */
    @GetMapping("/two")
    public R<List<CourseMgt2Vo>> getTwo(@RequestParam Long id) {
        return R.ok(courseMgtService.getTwo(id));
    }

    /**
     * 获取课程-第三阶段信息
     *
     * @param id 主键
     */
    @GetMapping("/three")
    public R<List<Teacher>> getThree(@RequestParam Long id) {
        return R.ok(courseMgtService.getThree(id));
    }

    /**
     * 新增或更新课程-第一阶段
     * 添加基础信息
     */
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/one")
    public R<Long> saveOrUpdateOne(@Validated(AddGroup.class) @RequestBody CourseMgt1Bo bo) {
        LoginUser user = LoginHelper.getLoginUser();
        bo.setCompanyId(user.getDeptId());
        bo.setCompanyName(user.getDeptName());
        Long id = courseMgtService.saveOrUpdateOne(bo);
        if (id != -1L){
            return R.ok("新增成功", id);
        }else {
            return R.fail("新增失败");
        }
    }


    /**
     * 新增课程-第二阶段
     * 添加教学计划信息
     */
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/two")
    public R<Void> saveOrUpdateTwo(@RequestBody List<CourseMgt2Bo> list) {
        boolean flag = courseMgtService.saveOrUpdateTwo(list);
        if (flag){
            return R.ok("新增成功");
        }else {
            return R.fail("新增失败");
        }
    }

    /**
     * 新增课程-第三阶段
     * 添加课程教师信息
     */
    @RepeatSubmit()
    @PostMapping("/three")
    public R<Void> saveOrUpdateThree(@RequestBody CourseMgt3Bo bo) {
        boolean flag = courseMgtService.saveOrUpdateThree(bo);
        if (flag){
            return R.ok("新增成功");
        }else {
            return R.fail("新增失败");
        }
    }



    /**
     * 修改课程
     */
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CourseBaseBo bo) {
        return toAjax(courseMgtService.updateByBo(bo));
    }

    /**
     * 删除课程
     *
     * @param ids 主键串
     */
    @Log(title = "课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(courseMgtService.deleteWithValidByIds(List.of(ids), true));
    }
    /**
     * 发布课程
     *
     * @param id 主键串
     */
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @GetMapping("/publish")
    public R<Void> publish(@RequestParam Long id) {
        Boolean b = courseMgtService.publish(id);
        if (b){
            return R.ok("发布成功");
        }else {
            return R.fail("发布失败");
        }
    }

    /**
     * 下架课程
     *
     * @param id 主键串
     */
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @GetMapping("/offShelf")
    public R<Void> offShelf(@RequestParam Long id) {
        Boolean b = courseMgtService.offShelf(id);
        if (b){
            return R.ok("下架成功");
        }else {
            return R.fail("下架失败");
        }
    }
}
