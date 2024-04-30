package org.dromara.media.api;


import java.util.List;
import java.util.Map;

/**
 * 远程媒资模块服务
 *
 * @author mumu
 * @date 2024/04/18
 */
public interface RemoteMediaService {

    /**
     * 通过mediaId查询多个mediaName
     *
     * @param mediaId   媒体id
     * @param mediaName 媒体名称
     * @return {@link Boolean}
     */
    Map<String, String> selectMediaNames(List<String> ids);

}
