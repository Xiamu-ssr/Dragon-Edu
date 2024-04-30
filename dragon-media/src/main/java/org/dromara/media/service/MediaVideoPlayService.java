package org.dromara.media.service;

import org.dromara.media.domain.vo.MediaVideoPlayVo;

import java.util.Map;

public interface MediaVideoPlayService {

    /**
     * 获取章节视频
     *
     * @param courseId    课程id
     * @param teachplanId 教学计划id
     * @return {@link Map}<{@link String}, {@link String}>
     *     <br/>
     *
     *     <div>path: video url path</div>
     */
    MediaVideoPlayVo getChapterVideo(Long userId, Long courseId, String teachplanId);
}
