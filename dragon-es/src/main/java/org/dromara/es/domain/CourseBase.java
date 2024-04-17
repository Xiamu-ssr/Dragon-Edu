package org.dromara.es.domain;

import lombok.Data;
import org.dromara.easyes.annotation.HighLight;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.FieldStrategy;
import org.dromara.easyes.annotation.rely.FieldType;
import org.dromara.easyes.annotation.rely.HighLightTypeEnum;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程管理对象 course_base
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
@IndexName("course_base")
public class CourseBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @IndexId
    private Long id;

    /**
     * 机构ID
     */
    @IndexField(exist = false)
    private Long companyId;

    /**
     * 课程名称
     */
    @IndexField(fieldType = FieldType.TEXT, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", strategy = FieldStrategy.NOT_EMPTY)
    @HighLight
    private String name;

    /**
     * 大分类
     */
    @IndexField(fieldType = FieldType.KEYWORD, strategy = FieldStrategy.NOT_EMPTY)
    private String mt;

    /**
     * 小分类
     */
    @IndexField(fieldType = FieldType.KEYWORD, strategy = FieldStrategy.NOT_EMPTY)
    private String st;

    /**
     * 课程图片
     */
    @IndexField(fieldType = FieldType.KEYWORD, strategy = FieldStrategy.NOT_EMPTY)
    private String pic;

    /**
     * 是否收费
     */
    @IndexField(fieldType = FieldType.BOOLEAN, strategy = FieldStrategy.NOT_NULL)
    private Boolean charge;

    /**
     * 原价
     */
    @IndexField(fieldType = FieldType.SCALED_FLOAT, scalingFactor = 100)
    private BigDecimal originalPrice;

    /**
     * 现价
     */
    @IndexField(fieldType = FieldType.SCALED_FLOAT, scalingFactor = 100)
    private BigDecimal price;

    /**
     * 评分
     */
    @IndexField(fieldType = FieldType.SCALED_FLOAT, scalingFactor = 10, strategy = FieldStrategy.NOT_NULL)
    private BigDecimal star;

    /**
     * UNPUBLISHED(1, "未发布"),     UNDER_REVIEW(2, "审核中"),     REVIEW_FAILED(3, "审核不通过"),     REVIEW_PASSED(4, "审核通过")
     */
    @IndexField(exist = false)
    private Integer status;

    /**
     * 审核意见
     */
    @IndexField(exist = false)
    private String mind;

    @IndexField(fieldType = FieldType.DATE, strategy = FieldStrategy.NOT_NULL)
    private LocalDateTime createTime;

    @IndexField(fieldType = FieldType.DATE, strategy = FieldStrategy.NOT_NULL)
    private LocalDateTime updateTime;
}




