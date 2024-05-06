package org.dromara.course.api;

import org.dromara.course.api.domain.CourseBase;

import java.util.List;
import java.util.Map;

/**
 * 程课程模块-课程信息服务
 *
 * @author mumu
 * @date 2024/04/28
 */
public interface RemoteCourseService {

    /**
     * 通过id获取课程(已发布)基础信息
     * <br/>
     * 已发布的课程信息
     *
     * @param courseId 课程id
     * @return {@link CourseBase}
     */
    CourseBase getCourseBaseById(Long courseId);

    /**
     * 查询某公司的课程数量
     * <br/>
     * 已发布的课程信息
     *
     * @param companyId 公司id
     * @return {@link Integer}
     */
    @Deprecated(since = "和查询章节数量统一了")
    Integer getCourseNum(Long companyId);

    /**
     * 获取某机构的课程数量和章节数量
     * <br/>
     * 依次
     *
     * @param companyId 公司id
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> getCourseNumAndTeachplanNum(Long companyId);

    /**
     * 批量获取课程图片s
     * <br/>
     * key: courseId
     * value: pic
     *
     * @param ids ids
     * @return {@link Map}<{@link Long}, {@link String}>
     */
    Map<Long, String> getCoursePics(List<Long> ids);
}
