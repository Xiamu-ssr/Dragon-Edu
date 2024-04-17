package org.dromara.course.domain.vo;

import java.math.BigDecimal;
import org.dromara.course.domain.CourseExtra;
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
 * 课程额外信息视图对象 course_extra
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CourseExtra.class)
public class CourseExtraVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键，课程id
     */
    @ExcelProperty(value = "主键，课程id")
    private Long id;

    /**
     * 机构名称
     */
    @ExcelProperty(value = "机构名称")
    private String companyName;

    /**
     * 课程标签，用符号,分割
     */
    @ExcelProperty(value = "课程标签，用符号,分割")
    private String tags;

    /**
     * 课程介绍
     */
    @ExcelProperty(value = "课程介绍")
    private String description;

    /**
     * 教师id列表，形如[''1'',''2'']
     */
    @ExcelProperty(value = "教师id列表，形如[''1'',''2'']")
    private String teachers;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String email;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String qq;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String wechat;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String phone;


}
