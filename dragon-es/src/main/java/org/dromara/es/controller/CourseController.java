package org.dromara.es.controller;


import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.domain.bo.CourseQueryBo;
import org.dromara.es.esmapper.CourseBaseMapper;
import org.dromara.es.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

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
        CourseBase baseOld = courseBaseMapper.selectById(courseBase.getId());
        if (baseOld == null){
            //没有这个es说明，第一次发布，将star设置为默认5.0
            courseBase.setStar(new BigDecimal("5.0"));
            return courseBaseMapper.insert(courseBase) > 0;
        }else {
            //如果这个索引存在，说明是更新后发布，此时不更新star。star额外设置新函数来让评论模块更新
            courseBase.setStar(baseOld.getStar());
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
