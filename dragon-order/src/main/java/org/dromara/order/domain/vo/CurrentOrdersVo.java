package org.dromara.order.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 最近订单-10个
 *
 * @author mumu
 * @date 2024/05/06
 */
@Data
public class CurrentOrdersVo {
    private Long courseId;
    private String courseName;
    private String pic;
    private BigDecimal price;
}
