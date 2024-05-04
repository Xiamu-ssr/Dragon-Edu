package org.dromara.learn.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.course.api.RemoteCourseService;
import org.dromara.course.api.domain.CourseBase;
import org.dromara.learn.domain.bo.ClassScheduleBo;
import org.dromara.learn.domain.vo.SimpleStatisticsVo;
import org.dromara.learn.service.ClassScheduleService;
import org.springframework.stereotype.Service;
import org.dromara.learn.domain.vo.ClassScheduleVo;
import org.dromara.learn.domain.ClassSchedule;
import org.dromara.learn.mapper.ClassScheduleMapper;

import java.util.List;
import java.util.Collection;

/**
 * 课程表Service业务层处理
 *
 * @author LionLi
 * @date 2024-04-27
 */
@RequiredArgsConstructor
@Service
public class ClassScheduleServiceImpl implements ClassScheduleService {

    private final ClassScheduleMapper baseMapper;

    @DubboReference
    RemoteCourseService remoteCourseService;

    /**
     * 查询课程表
     */
    @Override
    public ClassScheduleVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询课程表列表
     */
    @Override
    public TableDataInfo<ClassScheduleVo> queryPageList(ClassScheduleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ClassSchedule> lqw = buildQueryWrapper(bo);
        Page<ClassScheduleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程表列表
     */
    @Override
    public List<ClassScheduleVo> queryList(ClassScheduleBo bo) {
        LambdaQueryWrapper<ClassSchedule> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ClassSchedule> buildQueryWrapper(ClassScheduleBo bo) {
        LambdaQueryWrapper<ClassSchedule> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, ClassSchedule::getUserId, bo.getUserId());
        lqw.eq(bo.getCourseId() != null, ClassSchedule::getCourseId, bo.getCourseId());
        lqw.like(StringUtils.isNotBlank(bo.getCourseName()), ClassSchedule::getCourseName, bo.getCourseName());
        lqw.eq(bo.getLearningTime() != null, ClassSchedule::getLearningTime, bo.getLearningTime());
        return lqw;
    }

    /**
     * 新增课程表
     */
    @Override
    public Boolean insertByBo(ClassScheduleBo bo) {
        ClassSchedule add = MapstructUtils.convert(bo, ClassSchedule.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程表
     */
    @Override
    public Boolean updateByBo(ClassScheduleBo bo) {
        ClassSchedule update = MapstructUtils.convert(bo, ClassSchedule.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ClassSchedule entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除课程表
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean addFreeCourse(Long userId, Long courseId) {
        //判断课程是否免费
        CourseBase courseBase = remoteCourseService.getCourseBaseById(courseId);
        //免费则添加
        if (!courseBase.getCharge()){
            ClassScheduleBo scheduleBo = new ClassScheduleBo();
            scheduleBo.setUserId(userId);
            scheduleBo.setCourseId(courseBase.getId());
            scheduleBo.setCourseName(courseBase.getName());
            return insertByBo(scheduleBo);
        }else {
            return false;
        }
    }

    @Override
    public SimpleStatisticsVo simpleStatistics(Long userId) {
        SimpleStatisticsVo statisticsVo = new SimpleStatisticsVo();
        Long courseCount = baseMapper.selectCount(new LambdaQueryWrapper<ClassSchedule>()
            .eq(ClassSchedule::getUserId, userId)
        );
        statisticsVo.setCourseCount(courseCount);

        Long sumLearningTime = baseMapper.sumLearningTime(userId);
        statisticsVo.setLearnTimeCount(sumLearningTime);

        return statisticsVo;
    }
}
