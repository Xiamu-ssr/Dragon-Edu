package org.dromara.order.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单服务对象 order
 *
 * @author LionLi
 * @date 2024-04-27
 */
@Data
@TableName("order_table")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 采用雪花算法
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 订单状态：1未支付、2支付成功、3支付失败。
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;


}
