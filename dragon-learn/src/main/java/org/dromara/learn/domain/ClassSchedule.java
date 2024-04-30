package org.dromara.learn.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 课程表对象 class_schedule
 *
 * @author LionLi
 * @date 2024-04-27
 */
@Data
@TableName("class_schedule")
public class ClassSchedule implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     *
     */
    private String courseName;

    /**
     * 学习时长(分钟)
     */
    private Long learningTime;


}
