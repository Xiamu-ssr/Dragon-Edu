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
 * 课程评论对象 discuss
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Data
@TableName("discuss")
public class Discuss implements Serializable {

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
     * 机构id
     */
    private Long companyId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 头像地址url
     */
    private String avatar;

    /**
     * 评论时用户学习时长/分钟
     */
    private Long learnTime;

    /**
     * 评分
     */
    private BigDecimal star;

    /**
     * 评论状态。1正常2申请屏蔽3屏蔽
     */
    private Long status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
