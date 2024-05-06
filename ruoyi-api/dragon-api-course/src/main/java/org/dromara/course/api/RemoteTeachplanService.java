package org.dromara.course.api;


import java.util.Map;

/**
 * 远程课程模块-教学计划服务
 *
 *
 * @date 2024/04/16
 */
public interface RemoteTeachplanService {

    /**
     * 检查并获取视频信息-视频id-是否付费-是否试看<br/>
     * 判断章节是否属于课程
     *
     * @param courseId    课程id
     * @param teachplanId 教学计划id
     * @return {@link Map}<{@link String}, {@link String}>
     *     <br/>
     *     mediaId: <br/>
     *     isCharge: is Course need charge? <br/>
     *     isPreview: is teachplan support preview? <br/>
     */
    Map<String, Object> checkAndGetMedia(Long courseId, String teachplanId);

    /**
     * 查询某公司的章节数量
     * <br/>
     *
     * @param companyId 公司id
     * @return {@link Integer}
     */
    @Deprecated(since = "和查询课程数量统一了")
    Integer getTeachplanNum(Long companyId);

}
