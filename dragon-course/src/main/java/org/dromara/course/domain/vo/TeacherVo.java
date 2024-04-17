package org.dromara.course.domain.vo;

import org.dromara.course.domain.Teacher;
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
 * 教师管理视图对象 teacher
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Teacher.class)
public class TeacherVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 教师ID
     */
    @ExcelProperty(value = "教师ID")
    private String id;

    /**
     * 所属机构id
     */
    @ExcelProperty(value = "所属机构id")
    private Long companyId;

    /**
     * 教师姓名
     */
    @ExcelProperty(value = "教师姓名")
    private String name;

    /**
     * 教师职位
     */
    @ExcelProperty(value = "教师职位")
    private String position;

    /**
     * 教师介绍
     */
    @ExcelProperty(value = "教师介绍")
    private String introduction;

    /**
     * 教师头像
     */
    @ExcelProperty(value = "教师头像")
    private String photograph;


}
