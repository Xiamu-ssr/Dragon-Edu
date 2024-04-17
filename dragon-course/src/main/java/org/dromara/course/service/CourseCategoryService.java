package org.dromara.course.service;

import org.dromara.course.domain.CourseCategory;
import org.dromara.course.domain.vo.CourseCategoryVo;
import org.dromara.course.domain.bo.CourseCategoryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 分类管理Service接口
 *
 * @author Xiamu
 * @date 2024-03-29
 */
public interface CourseCategoryService {

    /**
     * 查询分类管理
     */
    CourseCategoryVo queryById(String id);

    /**
     * 查询分类管理列表
     */
    List<CourseCategoryVo> queryPageList(CourseCategoryBo bo);

    /**
     * 查询分类管理列表
     */
    List<CourseCategoryVo> queryList(CourseCategoryBo bo);

    /**
     * 新增分类管理
     */
    Boolean insertByBo(CourseCategoryBo bo);

    /**
     * 修改分类管理
     */
    Boolean updateByBo(CourseCategoryBo bo);

    /**
     * 校验并批量删除分类管理信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
