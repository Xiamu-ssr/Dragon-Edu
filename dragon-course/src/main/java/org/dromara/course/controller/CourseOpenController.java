package org.dromara.course.controller;

import org.dromara.course.domain.bo.CourseCategoryBo;
import org.dromara.course.domain.vo.CourseCategoryVo;
import org.dromara.course.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/open")
public class CourseOpenController {

    @Autowired
    CourseCategoryService categoryService;


    @GetMapping("/category/list")
    public List<CourseCategoryVo> list(CourseCategoryBo bo) {
        return categoryService.queryList(bo);
    }


}
