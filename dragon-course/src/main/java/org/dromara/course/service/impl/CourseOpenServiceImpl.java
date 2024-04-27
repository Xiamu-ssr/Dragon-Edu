package org.dromara.course.service.impl;

import com.alibaba.fastjson.JSON;
import org.dromara.common.core.domain.R;
import org.dromara.common.redis.utils.CourseHotUtils;
import org.dromara.course.domain.CourseAll;
import org.dromara.course.domain.CoursePublish;
import org.dromara.course.mapper.CoursePublishMapper;
import org.dromara.course.service.CourseHotService;
import org.dromara.course.service.CourseOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseOpenServiceImpl implements CourseOpenService {
    @Autowired
    CourseHotService courseHotService;
    @Autowired
    CoursePublishMapper coursePublishMapper;

    @Override
    public CourseAll getCourseAllInfo(Long courseId) {
        CourseAll courseAll = null;
        if (CourseHotUtils.isHotExist(courseId)){
            courseAll = courseHotService.queryById(courseId);
        }else {
            CoursePublish coursePublish = coursePublishMapper.selectById(courseId);
            if (coursePublish == null || coursePublish.getInfo() == null){
                return null;
            }
            String info = coursePublish.getInfo();
            courseAll = JSON.parseObject(info, CourseAll.class);
        }
        return courseAll;
    }
}
