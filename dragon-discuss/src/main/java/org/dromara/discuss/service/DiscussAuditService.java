package org.dromara.discuss.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.discuss.domain.Discuss;
import org.dromara.discuss.domain.bo.DiscussBo;
import org.dromara.discuss.domain.vo.DiscussVo;

import java.util.Collection;
import java.util.List;

/**
 * 课程评论Service接口
 *
 * @author LionLi
 * @date 2024-05-02
 */
public interface DiscussAuditService {

    /**
     * 查询课程评论列表
     */
    TableDataInfo<DiscussVo> queryPageList(DiscussBo bo, PageQuery pageQuery);
}
