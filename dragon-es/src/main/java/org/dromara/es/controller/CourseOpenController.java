package org.dromara.es.controller;


import com.github.benmanes.caffeine.cache.Cache;
import org.dromara.common.core.constant.CacheNames;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.domain.bo.CourseQueryBo;
import org.dromara.es.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    private CourseService courseService;

    @Autowired
    private Cache<String, EsPageInfo<CourseBase>> caffeineCache;

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


    /**
     * 首页热门查询
     *
     * @param bo bo
     * @return {@link EsPageInfo}<{@link CourseBase}>
     */
    @PostMapping("/homePageList")
    public EsPageInfo<CourseBase> homePageList(
        @RequestBody CourseQueryBo bo
    ) {
        return caffeineCache.get(
            CacheNames.HOMEPAGE_FIRST_HOT,
            key->courseService.homePageList(bo)
        );
    }


}
