package org.dromara.es.api;


/**
 * 远程课程模块-教学计划服务
 *
 *
 * @date 2024/04/16
 */
public interface RemoteTeachplanService {

    /**
     * 重新加载教学计划媒资名称
     *
     * @param mediaId   媒体id
     * @param mediaName 媒体名称
     * @return {@link Boolean}
     */
    @Deprecated
    Boolean reloadMediaName(Long mediaId, String mediaName);

}
