package org.dromara.course.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 教师管理对象 teacher
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@Data
@TableName("teacher")
public class Teacher implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 教师ID
     */
    @TableId(value = "id")
    private String id;

    /**
     * 所属机构id
     */
    private Long companyId;

    /**
     * 教师姓名
     */
    private String name;

    /**
     * 教师职位
     */
    private String position;

    /**
     * 教师介绍
     */
    private String introduction;

    /**
     * 教师头像
     */
    private String photograph;


}
