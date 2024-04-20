package org.dromara.es.api;


import org.dromara.es.api.domain.CourseBaseDto;

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

}
