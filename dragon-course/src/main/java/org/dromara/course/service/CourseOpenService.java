package org.dromara.course.service;

import org.dromara.course.domain.CourseAll;

public interface CourseOpenService {


    /**
     * 获取课程所有信息
     *
     * @param courseId 课程id
     * @return {@link CourseAll}
     */
    CourseAll getCourseAllInfo(Long courseId);

}
