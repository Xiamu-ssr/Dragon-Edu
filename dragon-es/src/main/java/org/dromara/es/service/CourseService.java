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
}
