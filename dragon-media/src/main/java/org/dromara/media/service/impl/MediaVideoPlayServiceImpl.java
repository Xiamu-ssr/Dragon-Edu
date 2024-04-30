package org.dromara.media.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.oss.core.OssClient;
import org.dromara.common.oss.factory.OssFactory;
import org.dromara.learn.api.RemoteScheduleService;
import org.dromara.course.api.RemoteTeachplanService;
import org.dromara.media.config.MinioProperties;
import org.dromara.media.domain.MediaFiles;
import org.dromara.media.domain.vo.MediaVideoPlayVo;
import org.dromara.media.mapper.MediaFilesMapper;
import org.dromara.media.service.MediaVideoPlayService;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class MediaVideoPlayServiceImpl implements MediaVideoPlayService {

    private final MinioProperties minioProperties;
    private final MediaFilesMapper mediaFilesMapper;

    @DubboReference
    RemoteTeachplanService remoteTeachplanService;
    @DubboReference
    RemoteScheduleService remoteScheduleService;

    @Override
    public MediaVideoPlayVo getChapterVideo(Long userId, Long courseId, String teachplanId) {
        MediaVideoPlayVo mediaVideoPlayVo = new MediaVideoPlayVo();
        //向course模块请求mediaId和course是否付费
        Map<String, Object> map = remoteTeachplanService.checkAndGetMedia(courseId, teachplanId);
        if (map == null){
            mediaVideoPlayVo.setCode(3);
            mediaVideoPlayVo.setMsg("此章节不存在或不属于此课程");
            return mediaVideoPlayVo;
        }
        //如果付费，查看用户是否拥有此课程，否则跳过
        boolean b1 = (boolean) map.get("isCharge");
        boolean b2 = (boolean) map.get("isPreview");
        if (b1 && !b2){
            boolean ownCourse = remoteScheduleService.isOwnCourse(userId, courseId);
            if (!ownCourse){
                mediaVideoPlayVo.setCode(2);
                mediaVideoPlayVo.setMsg("请先购买此课程");
                return mediaVideoPlayVo;
            }
        }
        //使用Minio有时限链接，返回media path
        MediaFiles mediaFiles = mediaFilesMapper.selectById((String) map.get("mediaId"));
        if (mediaFiles == null){
            mediaVideoPlayVo.setCode(3);
            mediaVideoPlayVo.setMsg("此章节视频不存在");
            return mediaVideoPlayVo;
        }
        OssClient ossClient = OssFactory.instance();
        String privateUrl = ossClient.getPrivateUrl(minioProperties.getVideoBucket(), mediaFiles.getPath(), 60*60*2);
        mediaVideoPlayVo.setCode(1);
        mediaVideoPlayVo.setPath(privateUrl);
        return mediaVideoPlayVo;
    }
}
