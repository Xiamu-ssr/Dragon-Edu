package org.dromara.course.domain;

import org.apache.ibatis.type.Alias;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程管理对象 course_base
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
@TableName("course_base")
@Alias("CourseBaseDomain")
public class CourseBase implements Serializable {

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
     * 课程名称
     */
    private String name;

    /**
     * 大分类
     */
    private String mt;

    /**
     * 小分类
     */
    private String st;

    /**
     * 课程图片
     */
    private String pic;

    /**
     * 是否收费
     */
    private Boolean charge;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 现价
     */
    private BigDecimal price;

    /**
     * 评分
     */
    @Deprecated(since = "基础表不存储星级，一是因为业务上用不到，二是因为评论模块不好更新这个属性，三是因为这个属性在其他地方不好同步。")
    private BigDecimal star;

    /**
     * UNPUBLISHED(1, "未发布"),     UNDER_REVIEW(2, "审核中"),     REVIEW_FAILED(3, "审核不通过"),     REVIEW_PASSED(4, "审核通过")
     */
    private Integer status;

    /**
     * 审核意见
     */
    private String mind;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}




