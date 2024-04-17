package org.dromara.es.api;


import org.dromara.es.api.domain.CourseBaseDto;

/**
 * 远程ES 索引服务
 *
 *
 * @date 2024/04/16
 */
public interface RemoteESIndexService {

    Boolean saveData2Index(CourseBaseDto courseBase);

}
