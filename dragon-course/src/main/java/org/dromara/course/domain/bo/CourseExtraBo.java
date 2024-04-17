package org.dromara.course.domain.bo;

import org.dromara.course.domain.CourseExtra;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
/**
 * 课程额外信息业务对象 course_extra
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
@AutoMapper(target = CourseExtra.class, reverseConvertGenerate = false)
public class CourseExtraBo implements Serializable {

    /**
     * 主键，课程id
     */
    @NotNull(message = "主键，课程id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 机构名称
     */
    @NotBlank(message = "机构名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String companyName;

    /**
     * 课程标签，用符号,分割
     */
    @NotBlank(message = "课程标签，用符号,分割不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tags;

    /**
     * 课程介绍
     */
    @NotBlank(message = "课程介绍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 教师id列表，形如[''1'',''2'']
     */
    @NotBlank(message = "教师id列表，形如[''1'',''2'']不能为空", groups = { AddGroup.class, EditGroup.class })
    private String teachers;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String email;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String qq;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String wechat;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;


}
