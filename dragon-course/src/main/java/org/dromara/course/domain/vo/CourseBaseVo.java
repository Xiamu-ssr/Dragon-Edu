package org.dromara.course.domain.vo;

import java.math.BigDecimal;
import org.dromara.course.domain.CourseBase;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 课程管理视图对象 course_base
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CourseBase.class)
public class CourseBaseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 机构ID
     */
    @ExcelProperty(value = "机构ID")
    private Long companyId;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String name;

    /**
     * 大分类
     */
    @ExcelProperty(value = "大分类")
    private String mt;

    /**
     * 小分类
     */
    @ExcelProperty(value = "小分类")
    private String st;

    /**
     * 课程图片
     */
    @ExcelProperty(value = "课程图片")
    private String pic;

    /**
     * 是否收费
     */
    @ExcelProperty(value = "是否收费")
    private Boolean charge;

    /**
     * 原价
     */
    @ExcelProperty(value = "原价")
    private BigDecimal originalPrice;

    /**
     * 现价
     */
    @ExcelProperty(value = "现价")
    private BigDecimal price;

    /**
     * 评分
     */
    @ExcelProperty(value = "评分")
    private BigDecimal star;

    /**
     * UNPUBLISHED(1, "未发布"),     UNDER_REVIEW(2, "审核中"),     REVIEW_FAILED(3, "审核不通过"),     REVIEW_PASSED(4, "审核通过")
     */
    @ExcelProperty(value = "课程状态")
    private Integer status;

    /**
     * 审核意见
     */
    @ExcelProperty(value = "审核意见")
    private String mind;


}
