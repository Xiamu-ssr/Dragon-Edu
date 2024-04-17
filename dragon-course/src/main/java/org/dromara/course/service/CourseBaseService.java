package org.dromara.course.service;

import org.dromara.course.domain.CourseBase;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.domain.bo.CourseBaseBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程管理Service接口
 *
 * @author Xiamu
 * @date 2024-04-12
 */
public interface CourseBaseService {

    /**
     * 查询课程管理
     */
    CourseBaseVo queryById(Long id);

    /**
     * 查询课程管理列表
     */
    TableDataInfo<CourseBaseVo> queryPageList(CourseBaseBo bo, PageQuery pageQuery);

    /**
     * 查询课程管理列表
     */
    List<CourseBaseVo> queryList(CourseBaseBo bo);

    /**
     * 新增课程管理
     */
    Boolean insertByBo(CourseBaseBo bo);

    /**
     * 修改课程管理
     */
    Boolean updateByBo(CourseBaseBo bo);

    /**
     * 校验并批量删除课程管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
