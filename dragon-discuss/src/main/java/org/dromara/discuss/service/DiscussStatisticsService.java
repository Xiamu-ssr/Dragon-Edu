package org.dromara.discuss.service;

import org.dromara.discuss.domain.DiscussStatistics;
import org.dromara.discuss.domain.vo.DiscussStatisticsVo;
import org.dromara.discuss.domain.bo.DiscussStatisticsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 评论统计，机构用Service接口
 *
 * @author LionLi
 * @date 2024-05-02
 */
public interface DiscussStatisticsService {

    /**
     * 查询评论统计，机构用
     */
    DiscussStatisticsVo queryById(Long id);

    /**
     * 查询评论统计，机构用
     */
    DiscussStatisticsVo queryByCourseId(Long courseId);

    /**
     * 查询评论统计，机构用列表
     */
    TableDataInfo<DiscussStatisticsVo> queryPageList(DiscussStatisticsBo bo, PageQuery pageQuery);

    /**
     * 查询评论统计，机构用列表
     */
    List<DiscussStatisticsVo> queryList(DiscussStatisticsBo bo);

    /**
     * 新增评论统计，机构用
     */
    Boolean insertByBo(DiscussStatisticsBo bo);

    /**
     * 修改评论统计，机构用
     */
    Boolean updateByBo(DiscussStatisticsBo bo);

    /**
     * 校验并批量删除评论统计，机构用信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
