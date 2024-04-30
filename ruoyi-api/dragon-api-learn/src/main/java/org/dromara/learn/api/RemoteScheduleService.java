package org.dromara.learn.api;


import java.util.Map;

/**
 * 远程课程模块-教学计划服务
 *
 *
 * @date 2024/04/16
 */
public interface RemoteScheduleService {

    /**
     * 用户是否拥有课程<br/>一般用于查询付费课程
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return boolean
     */
    boolean isOwnCourse(Long userId, Long courseId);

}
