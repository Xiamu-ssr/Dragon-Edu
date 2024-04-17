package org.dromara.course.controller;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.messagesdk.domain.MqMessage;
import org.dromara.common.messagesdk.mapper.MqMessageMapper;
import org.dromara.course.domain.CourseAll;

import org.dromara.course.domain.CoursePublish;
import org.dromara.course.mapper.CoursePublishMapper;
import org.dromara.es.api.RemoteESIndexService;
import org.dromara.es.api.domain.CourseBaseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("测试dubbo-es模块")
@ActiveProfiles("test")
class ESTest {

    @Autowired
    MqMessageMapper mqMessageMapper;
    @Autowired
    CoursePublishMapper coursePublishMapper;
    @DubboReference
    RemoteESIndexService remoteESIndexService;

    @Test
    public void addData2Index(){
        MqMessage mqMessage = mqMessageMapper.selectById(3);
        long courseId = Long.parseLong(mqMessage.getBusinessKey1());
        CoursePublish coursePublish = coursePublishMapper.selectById(courseId);
        CourseBaseDto courseBase = JSON.parseObject(coursePublish.getInfo(), CourseBaseDto.class);

        remoteESIndexService.saveData2Index(courseBase);

    }
}
