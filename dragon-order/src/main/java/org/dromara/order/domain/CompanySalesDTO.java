package org.dromara.order.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompanySalesDTO {
    private Long companyId;
    private Integer orderCount;
    private BigDecimal totalSales;
}
