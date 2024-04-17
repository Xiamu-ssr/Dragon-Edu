package org.dromara.course.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.course.domain.Teacher;
import org.dromara.course.domain.bo.*;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.domain.vo.CourseMgt1Vo;
import org.dromara.course.domain.vo.CourseMgt2Vo;

import java.util.Collection;
import java.util.List;

/**
 * 课程Service接口
 *
 * @author LionLi
 * @date 2024-03-10
 */
public interface CourseMgtService {

    /**
     * 查询课程
     */
    CourseBaseVo queryById(Long id);

    /**
     * 查询课程列表
     */
    TableDataInfo<CourseBaseVo> queryPageList(CourseBaseBo bo, PageQuery pageQuery);

    /**
     * 查询课程列表
     */
    List<CourseBaseVo> queryList(CourseBaseBo bo);

    /**
     * 获取课程-第一阶段信息
     */
    CourseMgt1Vo getOne(Long id);

    /**
     * 获取课程-第二阶段信息
     */
    List<CourseMgt2Vo> getTwo(Long id);

    /**
     * 获取课程-第三阶段信息
     */
    List<Teacher> getThree(Long id);

    /**
     * 新增课程-第一阶段
     */
    Long saveOrUpdateOne(CourseMgt1Bo bo);

    /**
     * 新增课程-第二阶段
     */
    Boolean saveOrUpdateTwo(List<CourseMgt2Bo> list);

    /**
     * 新增课程-第三阶段
     */
    Boolean saveOrUpdateThree(CourseMgt3Bo bo);

    /**
     * 修改课程
     */
    Boolean updateByBo(CourseBaseBo bo);

    /**
     * 校验并批量删除课程信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 发布课程
     * */
    Boolean publish(Long id);

    /**
     * 下架课程
     * */
    Boolean offShelf(Long id);
}
