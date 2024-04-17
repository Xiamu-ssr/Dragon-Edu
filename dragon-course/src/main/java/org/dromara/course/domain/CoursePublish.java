package org.dromara.course.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 课程发布对象 course_publish
 *
 * @author LionLi
 * @date 2024-04-13
 */
@Data
@TableName("course_publish")
public class CoursePublish implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 机构ID
     */
    private Long companyId;

    /**
     * 课程所有信息
     */
    private String info;


}
