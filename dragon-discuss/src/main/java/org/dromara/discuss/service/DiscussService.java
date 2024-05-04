package org.dromara.discuss.service;

import org.dromara.discuss.domain.Discuss;
import org.dromara.discuss.domain.vo.DiscussVo;
import org.dromara.discuss.domain.bo.DiscussBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程评论Service接口
 *
 * @author LionLi
 * @date 2024-05-02
 */
public interface DiscussService {

    /**
     * 查询课程评论
     */
    DiscussVo queryById(Long id);

    /**
     * 查询课程评论列表
     */
    TableDataInfo<DiscussVo> queryPageList(DiscussBo bo, PageQuery pageQuery);

    /**
     * 查询课程评论列表
     */
    List<DiscussVo> queryList(DiscussBo bo);

    /**
     * 新增课程评论
     */
    Boolean insertByBo(DiscussBo bo);

    /**
     * 修改课程评论
     */
    Boolean updateByBo(DiscussBo bo);

    /**
     * 校验并批量删除课程评论信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 将一条评论添加到统计中
     *
     * @param discuss 讨论
     * @return boolean
     */
    boolean addDiscussToStatistics(Discuss discuss);
}
