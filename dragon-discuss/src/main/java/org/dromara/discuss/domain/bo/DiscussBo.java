package org.dromara.discuss.domain.bo;

import org.dromara.discuss.domain.Discuss;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程评论业务对象 discuss
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Data
@AutoMapper(target = Discuss.class, reverseConvertGenerate = false)
public class DiscussBo implements Serializable {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 课程id
     */
    @NotNull(message = "课程id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long courseId;

    /**
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String courseName;

    /**
     * 机构id
     */
    @NotNull(message = "机构id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long companyId;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 头像地址url
     */
    @NotBlank(message = "头像地址url不能为空", groups = { AddGroup.class, EditGroup.class })
    private String avatar;

    /**
     * 评论时用户学习时长/分钟
     */
    @NotNull(message = "评论时用户学习时长/分钟不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long learnTime;

    /**
     * 评分
     */
    @NotNull(message = "评分不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal star;

    /**
     * 评论状态。1正常2申请屏蔽3屏蔽
     */
    @NotNull(message = "评论状态。1正常2申请屏蔽3屏蔽不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    private LocalDateTime createTime;
}
