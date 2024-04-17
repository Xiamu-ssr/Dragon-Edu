package org.dromara.media.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.media.domain.bo.MediaFilesBo;
import org.dromara.media.domain.vo.MediaFilesVo;

public interface MediaAuditService {

    /**
     * 查询media列表
     */
    TableDataInfo<MediaFilesVo> queryPageList(MediaFilesBo bo, PageQuery pageQuery);

    /**
     * 通过
     *
     * @param id 身份证件
     * @return {@link Boolean}
     */
    Boolean pass(String id);

    /**
     * 不通过
     *
     * @param id 身份证件
     * @return {@link Boolean}
     */
    Boolean reject(JsonNode bo);
}
