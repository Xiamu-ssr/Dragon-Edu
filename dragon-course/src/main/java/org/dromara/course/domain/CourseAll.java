package org.dromara.course.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.dromara.course.domain.vo.CourseMgt2Vo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 单个课程所有信息
 *
 * @author mumu
 * @date 2024/04/12
 */
@Data
public class CourseAll implements Serializable {
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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

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

    /**
     * 教学计划
     */
    private List<CourseMgt2Vo> teachplan;

    /**
     * 教师列表
     */
    private List<Teacher> teacherList;

}
