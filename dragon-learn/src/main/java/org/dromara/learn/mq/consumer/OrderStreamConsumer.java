package org.dromara.learn.mq.consumer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.mq.domain.OrderMessage;
import org.dromara.common.mq.domain.TestMessaging;
import org.dromara.learn.domain.ClassSchedule;
import org.dromara.learn.mapper.ClassScheduleMapper;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderStreamConsumer {

    @Autowired
    ClassScheduleMapper scheduleMapper;

    /**
     * 购买课程成功
     * <br></>
     * 后续操作-更新课程表
     *
     * @return {@link Consumer}<{@link TestMessaging}>
     */
    @Bean
    Consumer<OrderMessage> buyCourseSuccess() {
        return msg -> {
            Long courseId = msg.getCourseId();
            Long userId = msg.getUserId();
            boolean exist = scheduleMapper.exists(new LambdaQueryWrapper<ClassSchedule>()
                .eq(ClassSchedule::getUserId, userId)
                .eq(ClassSchedule::getCourseId, courseId));
            if (exist){
                log.info("用户{}课程表已存在课程{}", userId, courseId);
            }else {
                ClassSchedule schedule = new ClassSchedule();
                schedule.setUserId(userId);
                schedule.setCourseId(courseId);
                schedule.setCourseName(msg.getCourseName());
                schedule.setLearningTime(0L);
                boolean b = scheduleMapper.insert(schedule) > 0;
                if (b){
                    log.info("用户{}课程表添加课程{}", userId, courseId);
                }else {
                    throw new AmqpRejectAndDontRequeueException("插入ClassSchedule数据失败"+msg);
                }
            }
        };
    }
}
