package org.dromara.course.domain.bo;

import org.dromara.course.domain.CoursePublish;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.io.Serializable;
/**
 * 课程发布业务对象 course_publish
 *
 * @author LionLi
 * @date 2024-04-15
 */
@Data
@AutoMapper(target = CoursePublish.class, reverseConvertGenerate = false)
public class CoursePublishBo implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 机构ID
     */
    @NotNull(message = "机构ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long companyId;

    /**
     * 课程所有信息
     */
    @NotBlank(message = "课程所有信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String info;


}
