package org.dromara.course.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;
import org.dromara.course.domain.CourseCategory;
import org.dromara.course.domain.vo.CourseCategoryVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 分类管理Mapper接口
 *
 * @author Xiamu
 * @date 2024-03-29
 */
public interface CourseCategoryMapper extends BaseMapperPlus<CourseCategory, CourseCategoryVo> {

    List<CourseCategoryVo> selectCategoryList(@Param(Constants.WRAPPER) Wrapper<CourseCategory> queryWrapper);

}
