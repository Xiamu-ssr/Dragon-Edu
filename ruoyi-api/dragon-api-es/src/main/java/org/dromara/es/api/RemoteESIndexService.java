package org.dromara.es.api;


import org.dromara.common.core.domain.R;
import org.dromara.es.api.domain.CourseBaseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 远程ES 索引服务
 *
 *
 * @date 2024/04/16
 */
public interface RemoteESIndexService {

    /**
     * 保存数据到ES索引
     * <BR/>
     * course_base课程索引
     *
     * @param courseBase 球场基础
     * @return {@link Boolean}
     */
    Boolean saveData2Index(CourseBaseDto courseBase);

    /**
     * 设置课程的isHot属性
     *
     * @param id    身份证件
     * @param isHot 很热
     * @return {@link Boolean}
     */
    boolean setCourseHot(Long id, boolean isHot);


    /**
     * 更新课程的star
     *
     * @param courseId 课程id
     * @param star     明星
     * @return boolean
     */
    R<String> updateStar(Long courseId, BigDecimal star);

    /**
     * 批量更新课程的star
     *
     * @param star      明星
     * @param courseIds 课程ID
     * @return boolean
     */
    R<String> updateStarBatch(Map<Long, BigDecimal> courseStars);

}
