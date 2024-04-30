package org.dromara.media.domain.vo;


import lombok.Data;

/**
 * 媒体视频播放vo
 *
 * @author mumu
 * @date 2024/04/27
 */
@Data
public class MediaVideoPlayVo {
    /**
     * <div>code: 1.success, 2.need buy, 3.course doesn't have such teachplan</div>
     */
    private Integer code;
    //视频路径
    private String path;
    //备注消息
    private String msg;
}
