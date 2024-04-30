package org.dromara.course.api;

import org.dromara.course.api.domain.CourseBase;

/**
 * 程课程模块-课程信息服务
 *
 * @author mumu
 * @date 2024/04/28
 */
public interface RemoteCourseService {

    /**
     * 通过id获取课程(已发布)基础信息
     * <br/>
     * 已发布的课程信息
     *
     * @param courseId 课程id
     * @return {@link CourseBase}
     */
    CourseBase getCourseBaseById(Long courseId);
}
