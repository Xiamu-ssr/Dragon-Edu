package org.dromara.course.controller;

import org.dromara.common.core.domain.R;
import org.dromara.course.domain.CourseAll;
import org.dromara.course.service.CoursePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class CoursePublishController {
    @Autowired
    CoursePublishService publishService;

    /**
     * 获取课程发布版全部信息
     * <br/>
     * 如果是热门课程则使用redis，否则使用mysql
     *
     * @param courseId 身份证件
     * @return {@link R}<{@link CourseAll}>
     */
    @GetMapping("/courseAll/{courseId}")
    public R<CourseAll> getInfo(@PathVariable Long courseId) {
        CourseAll courseAll = publishService.getCourseAllInfo(courseId);
        if (courseAll != null){
            return R.ok(courseAll);
        }else {
            return R.fail("此课程不存在");
        }
    }
}
