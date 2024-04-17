package org.dromara.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.dromara.course.domain.Teachplan;
import org.dromara.course.domain.vo.TeachplanVo;
import org.dromara.course.domain.bo.TeachplanBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程计划Service接口
 *
 * @author Xiamu
 * @date 2024-03-22
 */
public interface TeachplanService extends IService<Teachplan> {

    /**
     * 查询课程计划
     */
    TeachplanVo queryById(String id);

    /**
     * 查询课程计划列表
     */
    TableDataInfo<TeachplanVo> queryPageList(TeachplanBo bo, PageQuery pageQuery);

    /**
     * 查询课程计划列表
     */
    List<TeachplanVo> queryList(TeachplanBo bo);

    /**
     * 新增课程计划
     */
    Boolean insertByBo(TeachplanBo bo);

    /**
     * 修改课程计划
     */
    Boolean updateByBo(TeachplanBo bo);

    /**
     * 校验并批量删除课程计划信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
