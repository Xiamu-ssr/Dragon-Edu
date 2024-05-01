package org.dromara.common.mq.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderMessage {

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

    private LocalDateTime createTime;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 选课状态：1选课成功、2待支付、3选课删除。
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;

}
