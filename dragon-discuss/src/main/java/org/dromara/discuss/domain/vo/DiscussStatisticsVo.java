package org.dromara.discuss.domain.vo;

import java.math.BigDecimal;
import org.dromara.discuss.domain.DiscussStatistics;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;



/**
 * 评论统计，机构用视图对象 discuss_statistics
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = DiscussStatistics.class)
public class DiscussStatisticsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 课程id
     */
    @ExcelProperty(value = "课程id")
    private Long courseId;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String courseName;

    /**
     * 课程图片
     */
    @ExcelProperty(value = "课程图片")
    private String pic;

    /**
     * 机构id
     */
    @ExcelProperty(value = "机构id")
    private Long companyId;

    /**
     * 评论数量
     */
    @ExcelProperty(value = "评论数量")
    private Long discussCount;

    /**
     * 评分-高精度
     */
    @ExcelProperty(value = "评分-高精度")
    private BigDecimal star;
}
