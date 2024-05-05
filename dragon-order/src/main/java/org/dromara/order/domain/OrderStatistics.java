package org.dromara.order.domain;

import lombok.AllArgsConstructor;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;

/**
 * 订单统计对象 order_statistics
 *
 * @author LionLi
 * @date 2024-05-05
 */
@Data
@TableName("order_statistics")
@AllArgsConstructor
public class OrderStatistics implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 机构id
     */
    private Long companyId;

    /**
     * 销售量
     */
    private Long saleNum;

    /**
     * 销售额
     */
    private BigDecimal sale;

    /**
     * 日期
     */
    private LocalDate statDate;


}
