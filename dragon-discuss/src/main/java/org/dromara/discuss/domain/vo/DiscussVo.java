package org.dromara.discuss.domain.vo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.discuss.domain.Discuss;
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
 * 课程评论视图对象 discuss
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Discuss.class)
public class DiscussVo implements Serializable {

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
     * 机构id
     */
    @ExcelProperty(value = "机构id")
    private Long companyId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名称
     */
    @ExcelProperty(value = "用户名称")
    private String userName;

    /**
     * 头像地址url
     */
    @ExcelProperty(value = "头像地址url")
    private String avatar;

    /**
     * 评论时用户学习时长/分钟
     */
    @ExcelProperty(value = "评论时用户学习时长/分钟")
    private Long learnTime;

    /**
     * 评论内容
     */
    @ExcelProperty(value = "评论内容")
    private String content;

    /**
     * 评分
     */
    @ExcelProperty(value = "评分")
    private BigDecimal star;

    /**
     * 评论状态。1正常2申请屏蔽3屏蔽
     */
    @ExcelProperty(value = "评论状态。1正常2申请屏蔽3屏蔽")
    private Long status;

    private LocalDateTime createTime;

}
