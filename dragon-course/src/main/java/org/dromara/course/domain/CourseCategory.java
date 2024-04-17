package org.dromara.course.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分类管理对象 course_category
 *
 * @author Xiamu
 * @date 2024-03-29
 */
@Data
@TableName("course_category")
public class CourseCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父结点id（第一级的父节点是0，自关联字段id）
     */
    private Long parentid;

    /**
     * 排序字段
     */
    private Long orderby;

    /**
     * 是否叶子
     */
    private Boolean isLeaf;


}
