package org.dromara.course.domain.vo;

import org.dromara.course.domain.CourseCategory;
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
 * 分类管理视图对象 course_category
 *
 * @author Xiamu
 * @date 2024-03-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CourseCategory.class)
public class CourseCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    private String name;

    /**
     * 父结点id（第一级的父节点是0，自关联字段id）
     */
    @ExcelProperty(value = "父结点id", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "第=一级的父节点是0，自关联字段id")
    private Long parentid;

    /**
     * 排序字段
     */
    @ExcelProperty(value = "排序字段")
    private Long orderby;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 是否叶子
     */
    @ExcelProperty(value = "是否叶子")
    private Boolean isLeaf;


}
