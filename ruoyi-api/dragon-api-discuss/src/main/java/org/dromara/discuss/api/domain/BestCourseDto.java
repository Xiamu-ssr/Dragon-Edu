package org.dromara.discuss.api.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BestCourseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 评分
     */
    private BigDecimal star;
}
