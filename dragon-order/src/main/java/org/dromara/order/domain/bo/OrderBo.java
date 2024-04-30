package org.dromara.order.domain.bo;

import org.dromara.order.domain.Order;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单服务业务对象 order
 *
 * @author LionLi
 * @date 2024-04-27
 */
@Data
@AutoMapper(target = Order.class, reverseConvertGenerate = false)
public class OrderBo implements Serializable {

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


    private LocalDateTime createTime;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal price;

    /**
     * 选课状态：1选课成功、2待支付、3选课删除。
     */
    @NotBlank(message = "订单状态：1未支付、2支付成功、3支付失败。不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remarks;


}
