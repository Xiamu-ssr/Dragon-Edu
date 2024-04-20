package org.dromara.es.domain.bo;

import lombok.Data;
import org.dromara.easyes.core.biz.OrderByParam;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程管理对象 course_base
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
public class CourseQueryBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 大分类
     */
    private String mt;

    /**
     * 小分类
     */
    private String st;

    /**
     * 是否收费
     */
    private Boolean charge;

    /**
     * 是否热门
     */
    private Boolean isHot;


    private OrderByParam orderByParam;

    private Integer pageNum;
    private Integer pageSize;

}




