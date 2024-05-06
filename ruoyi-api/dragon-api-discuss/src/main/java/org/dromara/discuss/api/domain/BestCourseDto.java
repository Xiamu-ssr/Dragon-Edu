package org.dromara.discuss.api.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BestCourseDto {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 评分
     */
    private BigDecimal star;
}
