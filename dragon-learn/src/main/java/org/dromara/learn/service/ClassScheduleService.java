package org.dromara.learn.service;

import org.dromara.learn.domain.bo.ClassScheduleBo;
import org.dromara.learn.domain.vo.ClassScheduleVo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.learn.domain.vo.SimpleStatisticsVo;

import java.util.Collection;
import java.util.List;

/**
 * 课程表Service接口
 *
 * @author LionLi
 * @date 2024-04-27
 */
public interface ClassScheduleService {

    /**
     * 查询课程表
     */
    ClassScheduleVo queryById(Long id);

    /**
     * 查询课程表列表
     */
    TableDataInfo<ClassScheduleVo> queryPageList(ClassScheduleBo bo, PageQuery pageQuery);

    /**
     * 查询课程表列表
     */
    List<ClassScheduleVo> queryList(ClassScheduleBo bo);

    /**
     * 新增课程表
     */
    Boolean insertByBo(ClassScheduleBo bo);

    /**
     * 修改课程表
     */
    Boolean updateByBo(ClassScheduleBo bo);

    /**
     * 校验并批量删除课程表信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 添加免费课程
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return {@link Boolean}
     */
    Boolean addFreeCourse(Long userId, Long courseId);


    /**
     * 用户简要数据统计
     *
     * @param userId 用户id
     * @return {@link SimpleStatisticsVo}
     */
    SimpleStatisticsVo simpleStatistics(Long userId);
}
