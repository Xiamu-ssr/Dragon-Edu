package org.dromara.course.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 课程计划对象 teachplan
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@Data
@TableName("teachplan")
public class Teachplan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 课程计划名称
     */
    private String pname;

    /**
     * 课程计划父级Id，大章节写"0"
     */
    private String parentid;

    /**
     * 层级，分为1、2级
     */
    private Long grade;

    /**
     * 章节及课程时介绍
     */
    private String description;

    /**
     * 排序字段
     */
    private Long orderby;

    /**
     * 课程标识
     */
    private Long courseId;

    /**
     * 媒资id
     */
    private String mediaId;

    /**
     * 媒资名称
     */
    private String mediaName;

    /**
     * 是否支持试学或预览（试看）
     */
    @JsonProperty("isPreview")
    private boolean isPreview;


}
