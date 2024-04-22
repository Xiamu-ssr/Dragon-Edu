package org.dromara.es.controller;


import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.domain.bo.CourseQueryBo;
import org.dromara.es.esmapper.CourseBaseMapper;
import org.dromara.es.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/open")
public class CourseOpenController {
    @Autowired
    private CourseService courseService;

    /**
     * 分页查询
     *
     * @param bo bo
     * @return {@link EsPageInfo}<{@link CourseBase}>
     */
    @PostMapping("/list")
    public EsPageInfo<CourseBase> pageList(
        @RequestBody CourseQueryBo bo
    ) {
        return courseService.pageList(bo, bo.getPageNum(), bo.getPageSize());
    }



}
