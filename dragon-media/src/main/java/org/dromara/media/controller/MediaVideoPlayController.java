package org.dromara.media.controller;

import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.media.domain.MediaFiles;
import org.dromara.media.domain.vo.MediaVideoPlayVo;
import org.dromara.media.mapper.MediaFilesMapper;
import org.dromara.media.service.MediaVideoPlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 开发端点
 *
 * @author mumu
 * @date 2024/04/24
 */
@RestController
@RequestMapping("/videoPlay")
public class MediaVideoPlayController {
    @Autowired
    MediaVideoPlayService mediaVideoPlayService;


    /**
     * 获取章节视频播放地址
     *
     * @param courseId    课程id
     * @param teachplanId 教学计划id
     * @return {@link R}<{@link String}>
     */
    @GetMapping("/getVideoUrl")
    public R<MediaVideoPlayVo> getChapterVideo(Long courseId, String teachplanId){
        Long userId = LoginHelper.getUserId();
        MediaVideoPlayVo vo = mediaVideoPlayService.getChapterVideo(userId, courseId, teachplanId);
        if (vo.getCode() == 1){
            return R.ok(StringUtils.isNotEmpty(vo.getMsg())?vo.getMsg():"视频播放成功~", vo);
        }else if (vo.getCode() == 2){
            return R.ok(StringUtils.isNotEmpty(vo.getMsg())?vo.getMsg():"请先购买~", vo);
        }else if (vo.getCode() == 3){
            return R.ok(StringUtils.isNotEmpty(vo.getMsg())?vo.getMsg():"此课程没有此章节~", vo);
        }else {
            return R.fail("未知异常~");
        }
    }


}
