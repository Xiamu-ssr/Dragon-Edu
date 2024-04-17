package org.dromara.course.domain.bo;

import org.dromara.course.domain.CourseBase;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
/**
 * 课程管理业务对象 course_base
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
@AutoMapper(target = CourseBase.class, reverseConvertGenerate = false)
public class CourseBaseBo implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 机构ID
     */
    @NotNull(message = "机构ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long companyId;

    /**
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 大分类
     */
    @NotBlank(message = "大分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mt;

    /**
     * 小分类
     */
    @NotBlank(message = "小分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String st;

    /**
     * 课程图片
     */
    @NotBlank(message = "课程图片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pic;

    /**
     * 是否收费
     */
    @NotNull(message = "是否收费不能为空", groups = { AddGroup.class, EditGroup.class })
    private Boolean charge;

    /**
     * 原价
     */
    @NotNull(message = "原价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal originalPrice;

    /**
     * 现价
     */
    @NotNull(message = "现价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal price;

    /**
     * 评分
     */
    @NotNull(message = "评分不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal star;

    /**
     * UNPUBLISHED(1, "未发布"),     UNDER_REVIEW(2, "审核中"),     REVIEW_FAILED(3, "审核不通过"),     REVIEW_PASSED(4, "审核通过")
     */
    @NotNull(message = "课程状态", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 审核意见
     */
    @NotBlank(message = "审核意见不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mind;


}
