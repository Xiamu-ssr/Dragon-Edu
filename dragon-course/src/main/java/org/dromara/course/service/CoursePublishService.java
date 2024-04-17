package org.dromara.course.service;

import org.dromara.course.domain.CoursePublish;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.domain.vo.CoursePublishVo;
import org.dromara.course.domain.bo.CoursePublishBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程发布Service接口
 *
 * @author LionLi
 * @date 2024-04-15
 */
public interface CoursePublishService {

    /**
     * 查询课程发布
     */
    CoursePublishVo queryById(Long id);

    /**
     * 查询课程发布列表
     */
    TableDataInfo<CourseBaseVo> queryPageList(CoursePublishBo bo, PageQuery pageQuery);

    /**
     * 查询课程发布列表
     */
    List<CoursePublishVo> queryList(CoursePublishBo bo);

    /**
     * 新增课程发布
     */
    Boolean insertByBo(CoursePublishBo bo);

    /**
     * 修改课程发布
     */
    Boolean updateByBo(CoursePublishBo bo);

    /**
     * 校验并批量删除课程发布信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
