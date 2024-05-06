package org.dromara.order.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

import java.io.Serial;
import java.io.Serializable;

/**
 * 销售总量统计对象 sale_data
 *
 * @author LionLi
 * @date 2024-05-06
 */
@Data
@TableName("sale_data")
public class SaleData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 销售总量
     */
    private Long saleNum;

    /**
     * 销售总额
     */
    private BigDecimal revenue;


}
