package org.dromara.learn.domain.vo;

import org.dromara.learn.domain.ClassSchedule;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 课程表视图对象 class_schedule
 *
 * @author LionLi
 * @date 2024-04-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ClassSchedule.class)
public class ClassScheduleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 课程id
     */
    @ExcelProperty(value = "课程id")
    private Long courseId;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String courseName;

    /**
     * 学习时长(分钟)
     */
    @ExcelProperty(value = "学习时长(分钟)")
    private Long learningTime;


}
