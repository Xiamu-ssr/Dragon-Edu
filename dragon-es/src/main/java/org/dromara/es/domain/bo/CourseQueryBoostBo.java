package org.dromara.es.domain.bo;

import lombok.Data;
import org.dromara.easyes.core.biz.OrderByParam;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程管理对象 course_base
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
public class CourseQueryBoostBo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 课程名称
     */
    public static final Float name = 10.0F;

    /**
     * 大分类
     */
    public static final Float mt = 3.0F;

    /**
     * 小分类
     */
    public static final Float st = 1.0F;

    /**
     * 是否收费
     */
    public static final Float charge = 1.0F;

    /**
     * 是否热门
     */
    public static final Float isHot = 1.0F;
}




