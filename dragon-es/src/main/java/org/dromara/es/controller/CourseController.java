package org.dromara.es.controller;


import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.domain.bo.CourseQueryBo;
import org.dromara.es.esmapper.CourseBaseMapper;
import org.dromara.es.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 课程索引
 *
 * @author mumu
 * @date 2024/04/24
 */
@RestController
@RequestMapping("/es")
public class CourseController {

    @Resource
    private CourseBaseMapper courseBaseMapper;
    @Autowired
    private CourseService courseService;

    @GetMapping("/createIndex")
    public void createIndex() {
        boolean success = courseBaseMapper.createIndex();
    }

    /**
     * 保存或更新
     *
     * @param courseBase 课程基础信息
     * @return boolean
     */
    @PostMapping("/save")
    public boolean save(@RequestBody CourseBase courseBase) {
        if (courseBaseMapper.selectById(courseBase.getId()) == null){
            return courseBaseMapper.insert(courseBase) > 0;
        }else {
            return courseBaseMapper.updateById(courseBase) > 0;
        }
    }

    /**
     * del
     *
     * @param id 身份证件
     * @return boolean
     */
    @DeleteMapping("/del/{id}")
    public boolean del(@PathVariable Long id) {
        return courseBaseMapper.deleteById(id) > 0;
    }

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
