package org.dromara.learn.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.learn.domain.ClassSchedule;
import org.dromara.learn.domain.bo.ClassScheduleBo;
import org.dromara.learn.domain.vo.ClassScheduleVo;
import org.dromara.learn.domain.vo.SimpleStatisticsVo;
import org.dromara.learn.mapper.ClassScheduleMapper;
import org.dromara.learn.service.ClassScheduleService;
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
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程表
 * 前端访问路由地址为:/order/schedule
 *
 * @author LionLi
 * @date 2024-04-27
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ClassScheduleController extends BaseController {

    private final ClassScheduleService classScheduleService;

    private final ClassScheduleMapper scheduleMapper;

    /**
     * 查询课程表列表
     * <br/>
     * 查询某个用户的课程表
     */
    @GetMapping("/list")
    public TableDataInfo<ClassScheduleVo> list(ClassScheduleBo bo, PageQuery pageQuery) {
        bo.setUserId(LoginHelper.getUserId());
        return classScheduleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程表列表
     */
    @Log(title = "课程表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ClassScheduleBo bo, HttpServletResponse response) {
        List<ClassScheduleVo> list = classScheduleService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程表", ClassScheduleVo.class, response);
    }

    /**
     * 获取课程表详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<ClassScheduleVo> getInfo(@NotNull(message = "主键不能为空")
                                      @PathVariable Long id) {
        return R.ok(classScheduleService.queryById(id));
    }

    /**
     * 新增课程表
     */
    @Log(title = "课程表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ClassScheduleBo bo) {
        return toAjax(classScheduleService.insertByBo(bo));
    }

    /**
     * 修改课程表
     */
    @Log(title = "课程表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ClassScheduleBo bo) {
        return toAjax(classScheduleService.updateByBo(bo));
    }

    /**
     * 删除课程表
     *
     * @param ids 主键串
     */
    @Log(title = "课程表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(classScheduleService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 加入一门免费课程到课程表
     *
     * @param id 主键串
     */
    @Log(title = "课程表", businessType = BusinessType.INSERT)
    @GetMapping("/addFreeCourse/{id}")
    public R<Void> addFreeCourse(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        Long userId = LoginHelper.getUserId();
        Boolean b = classScheduleService.addFreeCourse(userId, id);
        if (b){
            return R.ok("加入课程表成功");
        }else {
            return R.fail("付费课程需要支付");
        }
    }


    /**
     * 是否拥有某一门课程
     *
     * @param id 主键串
     */
    @Log(title = "课程表")
    @GetMapping("/isOwnCourse/{id}")
    public R<Boolean> isOwnCourse(@NotNull(message = "主键不能为空")
                                 @PathVariable Long id) {
        Long userId = LoginHelper.getUserId();
        boolean exists = scheduleMapper.exists(new LambdaQueryWrapper<ClassSchedule>()
            .eq(ClassSchedule::getUserId, userId)
            .eq(ClassSchedule::getCourseId, id));
        return R.ok(exists);
    }

    /**
     * 增加一门课程的学习时间1min
     *
     * @param id 主键串
     */
    @PatchMapping("/addLearnTime/{id}")
    public R<String> addLearnTime(
        @NotNull(message = "主键不能为空") @PathVariable Long id,
        @NotNull(message = "时间不能为空") @RequestBody LearnTimeDto learnTimeDto
    ) {
        Long userId = LoginHelper.getUserId();
        ClassSchedule schedule = scheduleMapper.selectOne(new LambdaQueryWrapper<ClassSchedule>()
            .eq(ClassSchedule::getUserId, userId)
            .eq(ClassSchedule::getCourseId, id));
        if (schedule != null){
            schedule.setLearningTime(schedule.getLearningTime() + learnTimeDto.getLearnTime());
            boolean b = scheduleMapper.updateById(schedule) > 0;
            if (!b){
                log.error("增加课程学习时间失败。用户{},增加时长{},课程表实体{}", userId, learnTimeDto.getLearnTime(), schedule);
            }
            return R.ok();
        }else {
            return R.fail("您未拥有本门课程，请先购买");
        }
    }

    @Data
    public static class LearnTimeDto {
        private Long learnTime;
    }

    /**
     * 用户简要统计数据
     *
     */
    @GetMapping("/simpleStatistics")
    public R<SimpleStatisticsVo> simpleStatistics() {
        Long userId = LoginHelper.getUserId();
        return R.ok(classScheduleService.simpleStatistics(userId));
    }
}
