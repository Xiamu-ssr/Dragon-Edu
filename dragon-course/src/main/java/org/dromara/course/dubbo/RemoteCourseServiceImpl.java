package org.dromara.course.dubbo;

import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.course.api.RemoteCourseService;
import org.dromara.course.api.domain.CourseBase;
import org.dromara.course.service.CourseHotService;
import org.dromara.course.service.CoursePublishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class RemoteCourseServiceImpl implements RemoteCourseService {
    @Autowired
    CoursePublishService publishService;

    @Override
    public CourseBase getCourseBaseById(Long courseId) {
        org.dromara.course.domain.CourseBase baseInfo = publishService.getCourseBaseInfo(courseId);
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(baseInfo, courseBase);
        return courseBase;
    }
}
