package org.dromara.course.domain.vo;

import org.dromara.course.domain.CoursePublish;
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
 * 课程发布视图对象 course_publish
 *
 * @author LionLi
 * @date 2024-04-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoursePublish.class)
public class CoursePublishVo implements Serializable {

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
     * 课程所有信息
     */
    @ExcelProperty(value = "课程所有信息")
    private String info;


}
