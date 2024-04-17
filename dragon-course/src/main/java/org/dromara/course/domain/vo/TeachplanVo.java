package org.dromara.course.domain.vo;

import org.dromara.course.domain.Teachplan;
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
 * 课程计划视图对象 teachplan
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Teachplan.class)
public class TeachplanVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @ExcelProperty(value = "UUID")
    private String id;

    /**
     * 课程计划名称
     */
    @ExcelProperty(value = "课程计划名称")
    private String pname;

    /**
     * 课程计划父级Id，大章节写"0"
     */
    @ExcelProperty(value = "课程计划父级Id，大章节写\"0\"")
    private String parentid;

    /**
     * 层级，分为1、2级
     */
    @ExcelProperty(value = "层级，分为1、2级")
    private Long grade;

    /**
     * 章节及课程时介绍
     */
    @ExcelProperty(value = "章节及课程时介绍")
    private String description;

    /**
     * 排序字段
     */
    @ExcelProperty(value = "排序字段")
    private Long orderby;

    /**
     * 课程标识
     */
    @ExcelProperty(value = "课程标识")
    private Long courseId;

    /**
     * 媒资id
     */
    @ExcelProperty(value = "媒资id")
    private String mediaId;

    /**
     * 媒资名称
     */
    private String mediaName;

    /**
     * 是否支持试学或预览（试看）
     */
    @ExcelProperty(value = "是否支持试学或预览", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "试=看")
    private Integer isPreview;


}
