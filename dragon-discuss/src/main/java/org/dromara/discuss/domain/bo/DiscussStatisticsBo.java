package org.dromara.discuss.domain.bo;

import org.dromara.discuss.domain.DiscussStatistics;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
/**
 * 评论统计，机构用业务对象 discuss_statistics
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Data
@AutoMapper(target = DiscussStatistics.class, reverseConvertGenerate = false)
public class DiscussStatisticsBo implements Serializable {

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
     * 课程图片
     */
    @NotBlank(message = "课程图片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pic;

    /**
     * 机构id
     */
    @NotNull(message = "机构id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long companyId;

    /**
     * 评论数量
     */
    @NotNull(message = "评论数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long discussCount;

    /**
     * 评分-高精度
     */
    @NotNull(message = "评分-高精度不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal star;


}
