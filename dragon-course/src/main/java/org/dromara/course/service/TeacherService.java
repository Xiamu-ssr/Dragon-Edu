package org.dromara.course.service;

import org.dromara.course.domain.Teacher;
import org.dromara.course.domain.vo.TeacherVo;
import org.dromara.course.domain.bo.TeacherBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 教师管理Service接口
 *
 * @author Xiamu
 * @date 2024-03-22
 */
public interface TeacherService {

    /**
     * 查询教师管理
     */
    TeacherVo queryById(String id, Long CompanyId);

    /**
     * 查询教师管理列表
     */
    TableDataInfo<TeacherVo> queryPageList(TeacherBo bo, PageQuery pageQuery);

    /**
     * 查询教师管理列表
     */
    List<TeacherVo> queryList(TeacherBo bo);

    /**
     * 新增教师管理
     */
    Boolean insertByBo(TeacherBo bo);

    /**
     * 修改教师管理
     */
    Boolean updateByBo(TeacherBo bo);

    /**
     * 校验并批量删除教师管理信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
