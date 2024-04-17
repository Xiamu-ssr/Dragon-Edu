package org.dromara.course.domain.bo;

import org.dromara.course.domain.CourseCategory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.io.Serializable;
/**
 * 分类管理业务对象 course_category
 *
 * @author Xiamu
 * @date 2024-03-29
 */
@Data
@AutoMapper(target = CourseCategory.class, reverseConvertGenerate = false)
public class CourseCategoryBo implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;


    private Long parentid;

    /**
     * 排序字段
     */
    @NotNull(message = "排序字段不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderby;

    /**
     * 是否叶子
     */
    private Boolean isLeaf;


}
