package org.dromara.course.controller;

import com.alibaba.fastjson.JSON;
import org.dromara.common.core.domain.R;
import org.dromara.common.redis.utils.CourseHotUtils;
import org.dromara.course.domain.CourseAll;
import org.dromara.course.domain.CoursePublish;
import org.dromara.course.domain.bo.CourseCategoryBo;
import org.dromara.course.domain.vo.CourseCategoryVo;
import org.dromara.course.mapper.CoursePublishMapper;
import org.dromara.course.service.CourseCategoryService;
import org.dromara.course.service.CourseHotService;
import org.dromara.course.service.CourseOpenService;
import org.dromara.course.service.CoursePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 开放端点
 *
 * @author mumu
 * @date 2024/04/24
 */
@RestController
@RequestMapping("/open")
public class CourseOpenController {

    @Autowired
    CourseCategoryService categoryService;
    @Autowired
    CourseOpenService courseOpenService;


    @GetMapping("/category/list")
    public List<CourseCategoryVo> list(CourseCategoryBo bo) {
        return categoryService.queryList(bo);
    }


}
