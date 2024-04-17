package org.dromara.course.domain.bo;

import org.dromara.course.domain.Teacher;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.io.Serializable;
/**
 * 教师管理业务对象 teacher
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@Data
@AutoMapper(target = Teacher.class, reverseConvertGenerate = false)
public class TeacherBo implements Serializable {

    /**
     * 教师ID
     */
    @NotBlank(message = "教师ID不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 所属机构id
     */
    @NotNull(message = "所属机构id不能为空", groups = { EditGroup.class })
    private Long companyId;

    /**
     * 教师姓名
     */
    @NotBlank(message = "教师姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 教师职位
     */
    @NotBlank(message = "教师职位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String position;

    /**
     * 教师介绍
     */
    @NotBlank(message = "教师介绍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String introduction;

    /**
     * 教师头像
     */
    @NotBlank(message = "教师头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String photograph;


}
