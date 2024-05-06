package org.dromara.discuss.api;


import org.dromara.discuss.api.domain.BestCourseDto;
import org.dromara.discuss.api.domain.RemoteDiscussCourseBaseDto;

import java.util.List;
import java.util.Map;

/**
 * 远程课程模块-教学计划服务
 *
 *
 * @date 2024/04/16
 */
public interface RemoteDiscussService {

    /**
     * 课程发布，为新发布的课程创建一条新的默认统计数据。如果存在则不管
     * <br/>
     * 不创建也没关系，因为评论时会更新statistics统计数据，如果没有，则新建一条默认
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return boolean
     */
    boolean createNewStatistics(RemoteDiscussCourseBaseDto dto);


    /**
     * 查询机构评论总数
     *
     * @param company_id 公司id
     * @return {@link Integer}
     */
    Integer getDiscussNum(Long company_id);


    /**
     * 查询最佳课程（默认5个）
     *
     * @param company_id 公司id
     * @return {@link Integer}
     */
    List<BestCourseDto> getBestCourse(Long company_id);

}
