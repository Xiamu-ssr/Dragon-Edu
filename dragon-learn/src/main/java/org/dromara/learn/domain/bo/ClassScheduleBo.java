package org.dromara.learn.domain.bo;

import org.dromara.learn.domain.ClassSchedule;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.io.Serializable;
/**
 * 课程表业务对象 class_schedule
 *
 * @author LionLi
 * @date 2024-04-27
 */
@Data
@AutoMapper(target = ClassSchedule.class, reverseConvertGenerate = false)
public class ClassScheduleBo implements Serializable {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 课程id
     */
    @NotNull(message = "课程id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long courseId;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String courseName;

    /**
     * 学习时长(分钟)
     */
    private Long learningTime;


}
