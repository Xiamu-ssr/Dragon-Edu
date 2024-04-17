package org.dromara.course.domain.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 课程管理-阶段1 bo
 *
 * @author mumu
 * @date 2024/03/23
 */
@Data
public class CourseMgt1Bo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 机构ID
     */
    private Long companyId;

    /**
     * 机构名称
     */
    private String companyName;

    /**
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 课程标签
     */
    @NotBlank(message = "课程标签不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tags;

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
     * 课程介绍
     */
    @NotBlank(message = "课程介绍不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 课程图片
     */
    @NotBlank(message = "课程图片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pic;

    /**
     * 收费规则，对应数据字典
     */
    private Boolean charge;

    /**
     * 现价
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originalPrice;

    /**
     * 教师id列表
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

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 未发布,审核中,审核不通过,审核通过,已发布
     */
    private Integer status;

    /**
     * 审核意见
     */
    private String mind;

}
