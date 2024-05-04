package org.dromara.learn.domain.vo;

import lombok.Data;

@Data
public class SimpleStatisticsVo {

    /**
     * 课程数量
     */
    private Long courseCount;

    /**
     * 总学习时长
     */
    private Long learnTimeCount;

}
