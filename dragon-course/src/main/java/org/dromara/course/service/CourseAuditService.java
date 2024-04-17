package org.dromara.course.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.course.domain.bo.CourseBaseBo;
import org.dromara.course.domain.vo.CourseBaseVo;

public interface CourseAuditService {

    /**
     * 查询待审核课程列表
     */
    TableDataInfo<CourseBaseVo> queryPageList(CourseBaseBo bo, PageQuery pageQuery);

    /**
     * 课程审核通过
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean pass(Long id);


    /**
     * 课程审核不通过
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean reject(JsonNode bo);

}
