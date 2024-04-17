package org.dromara.course.service;

import org.dromara.course.domain.CourseExtra;
import org.dromara.course.domain.vo.CourseExtraVo;
import org.dromara.course.domain.bo.CourseExtraBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程额外信息Service接口
 *
 * @author Xiamu
 * @date 2024-04-12
 */
public interface CourseExtraService {

    /**
     * 查询课程额外信息
     */
    CourseExtraVo queryById(Long id);

    /**
     * 查询课程额外信息列表
     */
    TableDataInfo<CourseExtraVo> queryPageList(CourseExtraBo bo, PageQuery pageQuery);

    /**
     * 查询课程额外信息列表
     */
    List<CourseExtraVo> queryList(CourseExtraBo bo);

    /**
     * 新增课程额外信息
     */
    Boolean insertByBo(CourseExtraBo bo);

    /**
     * 修改课程额外信息
     */
    Boolean updateByBo(CourseExtraBo bo);

    /**
     * 校验并批量删除课程额外信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
