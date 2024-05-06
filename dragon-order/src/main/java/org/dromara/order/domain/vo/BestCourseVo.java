package org.dromara.order.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 最佳课程
 *
 * @author mumu
 * @date 2024/05/06
 */
@Data
public class BestCourseVo {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 评分
     */
    private BigDecimal star;
}
