package org.dromara.course.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

import java.io.Serial;
import java.io.Serializable;

/**
 * 课程额外信息对象 course_extra
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
@TableName("course_extra")
public class CourseExtra implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键，课程id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 机构名称
     */
    private String companyName;

    /**
     * 课程标签，用符号,分割
     */
    private String tags;

    /**
     * 课程介绍
     */
    private String description;

    /**
     * 教师id列表，形如[''1'',''2'']
     */
    private String teachers;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private String qq;

    /**
     *
     */
    private String wechat;

    /**
     *
     */
    private String phone;


}
