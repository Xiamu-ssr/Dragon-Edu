package org.dromara.learn.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.learn.domain.ClassSchedule;
import org.dromara.learn.mapper.ClassScheduleMapper;
import org.dromara.learn.api.RemoteScheduleService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class RemoteScheduleServiceImpl implements RemoteScheduleService {

    @Autowired
    ClassScheduleMapper scheduleMapper;

    @Override
    public boolean isOwnCourse(Long userId, Long courseId) {
        LambdaQueryWrapper<ClassSchedule> queryWrapper = new LambdaQueryWrapper<ClassSchedule>()
            .eq(ClassSchedule::getUserId, userId)
            .eq(ClassSchedule::getCourseId, courseId);
        ClassSchedule schedule = scheduleMapper.selectOne(queryWrapper);
        return schedule != null;
    }

    @Override
    public long userLearnTime(Long userId, Long courseId) {
        ClassSchedule schedule = scheduleMapper.selectOne(new LambdaQueryWrapper<ClassSchedule>()
            .select(ClassSchedule::getLearningTime)
            .eq(ClassSchedule::getUserId, userId)
            .eq(ClassSchedule::getCourseId, courseId)
        );
        return schedule.getLearningTime();
    }
}
