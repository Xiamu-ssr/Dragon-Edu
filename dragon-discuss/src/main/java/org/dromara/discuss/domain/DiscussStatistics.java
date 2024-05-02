package org.dromara.discuss.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论统计，机构用对象 discuss_statistics
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Data
@TableName("discuss_statistics")
public class DiscussStatistics implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程图片
     */
    private String pic;

    /**
     * 机构id
     */
    private Long companyId;

    /**
     * 评论数量
     */
    private Long discussCount;

    /**
     * 评分-高精度
     */
    private BigDecimal star;


}
