package org.dromara.es.service;

import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.domain.bo.CourseQueryBo;

public interface CourseService {


    /**
     * 分页查询
     *
     * @param courseQueryBo bo基地
     * @param pageNum      书籍页码
     * @param pageSize     页面大小
     * @return {@link EsPageInfo}<{@link CourseBase}>
     */
    EsPageInfo<CourseBase> pageList(CourseQueryBo courseQueryBo, Integer pageNum, Integer pageSize);

    /**
     * 首页热门查询
     *
     * @param bo bo
     * @return {@link EsPageInfo}<{@link CourseBase}>
     */
    EsPageInfo<CourseBase> homePageList(CourseQueryBo bo);

    /**
     * 设置课程的isHot属性
     *
     * @param id    身份证件
     * @param isHot 很热
     * @return {@link Boolean}
     */
    boolean setCourseHot(Long id, boolean isHot);
}
