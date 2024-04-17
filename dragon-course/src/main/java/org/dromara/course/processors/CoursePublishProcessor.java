package org.dromara.course.processors;

import com.alibaba.fastjson2.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.messagesdk.domain.MqMessage;
import org.dromara.common.messagesdk.enums.MessageTypeEnum;
import org.dromara.common.messagesdk.service.MessageProcessAbstract;
import org.dromara.common.messagesdk.service.MqMessageService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.course.domain.*;
import org.dromara.course.domain.vo.CourseMgt2Vo;
import org.dromara.course.mapper.CourseBaseMapper;
import org.dromara.course.mapper.CourseExtraMapper;
import org.dromara.course.mapper.CoursePublishMapper;
import org.dromara.course.mapper.TeacherMapper;
import org.dromara.course.service.CourseMgtService;
import org.dromara.es.api.RemoteESIndexService;
import org.dromara.es.api.domain.CourseBaseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CoursePublishProcessor extends MessageProcessAbstract implements BasicProcessor {

    /**
     * key1: courseId
     * */

    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseExtraMapper courseExtraMapper;
    @Autowired
    CourseMgtService courseMgtService;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CoursePublishMapper coursePublishMapper;
    @DubboReference
    RemoteESIndexService remoteESIndexService;


    @Override
    public ProcessResult process(TaskContext context) throws Exception {
        int shardIndex = 0;
        int shardTotal = 1;
        process(context, shardIndex, shardTotal, MessageTypeEnum.COURSE_PUBLISH.getValue(), 8, 60);

        return new ProcessResult(true, context + ": " + true);
    }

    @Override
    public boolean execute(MqMessage mqMessage) {
        boolean b1 = updateMysql(mqMessage);
        if (!b1){
            return false;
        }
        boolean b2 = updateES(mqMessage);
        if (!b2){
            return false;
        }
        return updateRedis(mqMessage);
    }

    /**
     * 1. update mysql table called "course_publish"
     */
    public boolean updateMysql(MqMessage mqMessage){
        Long taskId = mqMessage.getId();
        Long courseId = Long.valueOf(mqMessage.getBusinessKey1());
        //幂等性处理
        //取出该阶段的执行状态，如果为不为0，则无需处理
        MqMessageService mqMessageService = this.getMqMessageService();
        int stageOne = mqMessageService.getStageOne(taskId);
        if (stageOne > 0){
            log.debug("update mysql任务完成，无需处理");
            return true;
        }
        //get course all info
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        CourseExtra courseExtra = courseExtraMapper.selectById(courseId);
        List<CourseMgt2Vo> teachplan = courseMgtService.getTwo(courseId);
        List<Long> teacherIds = Arrays.stream(courseExtra.getTeachers().split(",")).map(Long::parseLong).toList();
        List<Teacher> teachers = teacherMapper.selectBatchIds(teacherIds);
        //combine them
        CourseAll courseAll = new CourseAll();
        BeanUtils.copyProperties(courseBase, courseAll);
        BeanUtils.copyProperties(courseExtra, courseAll);
        courseAll.setTeachplan(teachplan);
        courseAll.setTeacherList(teachers);
        //update into "course_publish"
        CoursePublish coursePublish = new CoursePublish();
        coursePublish.setId(courseId);
        coursePublish.setCompanyId(courseAll.getCompanyId());
        coursePublish.setInfo(JSON.toJSONString(courseAll));
        boolean b = coursePublishMapper.insertOrUpdate(coursePublish);
        if (!b){
            return false;
        }
        //处理完成修改对应数据状态
        mqMessageService.completedStageOne(taskId);
        return true;
    }



    /**
     * 2. update es
     */
    public boolean updateES(MqMessage mqMessage){
        Long taskId = mqMessage.getId();
        Long courseId = Long.valueOf(mqMessage.getBusinessKey1());

        //幂等性处理
        //取出该阶段的执行状态，如果为不为0，则无需处理
        MqMessageService mqMessageService = this.getMqMessageService();
        int stageTwo = mqMessageService.getStageTwo(taskId);
        if (stageTwo > 0){
            log.debug("update es任务完成，无需处理");
            return true;
        }
        //获取course_all数据，注入到course_base中
        CoursePublish coursePublish = coursePublishMapper.selectById(courseId);
        CourseBaseDto courseBase = JSON.parseObject(coursePublish.getInfo(), CourseBaseDto.class);
        Boolean b = remoteESIndexService.saveData2Index(courseBase);
        if (!b){
            return false;
        }
        //处理完成修改对应数据状态
        mqMessageService.completedStageTwo(taskId);
        return true;
    }


    /**
     * 3. update redis
     */
    public boolean updateRedis(MqMessage mqMessage){
        Long taskId = mqMessage.getId();
        Long courseId = Long.valueOf(mqMessage.getBusinessKey1());

        //幂等性处理
        //取出该阶段的执行状态，如果为不为0，则无需处理
        MqMessageService mqMessageService = this.getMqMessageService();
        int stageThree = mqMessageService.getStageThree(taskId);
        if (stageThree > 0){
            log.debug("update redis任务完成，无需处理");
            return true;
        }
        //:todo

        //处理完成修改对应数据状态
        mqMessageService.completedStageThree(taskId);
        return true;
    }
}
