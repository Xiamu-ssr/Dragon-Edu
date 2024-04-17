package org.dromara.course.domain.bo;

import org.dromara.course.domain.Teachplan;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.io.Serializable;
/**
 * 课程计划业务对象 teachplan
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@Data
@AutoMapper(target = Teachplan.class, reverseConvertGenerate = false)
public class TeachplanBo implements Serializable {

    /**
     * UUID
     */
    @NotBlank(message = "UUID不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 课程计划名称
     */
    @NotBlank(message = "课程计划名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pname;

    /**
     * 课程计划父级Id，大章节写"0"
     */
    @NotBlank(message = "课程计划父级Id，大章节写\"0\"不能为空", groups = { AddGroup.class, EditGroup.class })
    private String parentid;

    /**
     * 层级，分为1、2级
     */
    @NotNull(message = "层级，分为1、2级不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long grade;

    /**
     * 章节及课程时介绍
     */
    @NotBlank(message = "章节及课程时介绍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 排序字段
     */
    @NotNull(message = "排序字段不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderby;

    /**
     * 课程标识
     */
    @NotNull(message = "课程标识不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long courseId;

    /**
     * 媒资id
     */
    @NotNull(message = "媒资id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mediaId;

    /**
     * 媒资名称
     */
    private String mediaName;

    /**
     * 是否支持试学或预览（试看）
     */
    @NotNull(message = "是否支持试学或预览（试看）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer isPreview;


}
