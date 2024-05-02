package org.dromara.discuss.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.discuss.domain.bo.DiscussStatisticsBo;
import org.dromara.discuss.domain.vo.DiscussStatisticsVo;
import org.dromara.discuss.domain.DiscussStatistics;
import org.dromara.discuss.mapper.DiscussStatisticsMapper;
import org.dromara.discuss.service.DiscussStatisticsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 评论统计，机构用Service业务层处理
 *
 * @author LionLi
 * @date 2024-05-02
 */
@RequiredArgsConstructor
@Service
public class DiscussStatisticsServiceImpl implements DiscussStatisticsService {

    private final DiscussStatisticsMapper baseMapper;

    /**
     * 查询评论统计，机构用
     */
    @Override
    public DiscussStatisticsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询评论统计，机构用列表
     */
    @Override
    public TableDataInfo<DiscussStatisticsVo> queryPageList(DiscussStatisticsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<DiscussStatistics> lqw = buildQueryWrapper(bo);
        Page<DiscussStatisticsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询评论统计，机构用列表
     */
    @Override
    public List<DiscussStatisticsVo> queryList(DiscussStatisticsBo bo) {
        LambdaQueryWrapper<DiscussStatistics> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<DiscussStatistics> buildQueryWrapper(DiscussStatisticsBo bo) {
        LambdaQueryWrapper<DiscussStatistics> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCourseId() != null, DiscussStatistics::getCourseId, bo.getCourseId());
        lqw.like(StringUtils.isNotBlank(bo.getCourseName()), DiscussStatistics::getCourseName, bo.getCourseName());
        lqw.eq(StringUtils.isNotBlank(bo.getPic()), DiscussStatistics::getPic, bo.getPic());
        lqw.eq(bo.getCompanyId() != null, DiscussStatistics::getCompanyId, bo.getCompanyId());
        lqw.eq(bo.getDiscussCount() != null, DiscussStatistics::getDiscussCount, bo.getDiscussCount());
        lqw.eq(bo.getStar() != null, DiscussStatistics::getStar, bo.getStar());
        return lqw;
    }

    /**
     * 新增评论统计，机构用
     */
    @Override
    public Boolean insertByBo(DiscussStatisticsBo bo) {
        DiscussStatistics add = MapstructUtils.convert(bo, DiscussStatistics.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改评论统计，机构用
     */
    @Override
    public Boolean updateByBo(DiscussStatisticsBo bo) {
        DiscussStatistics update = MapstructUtils.convert(bo, DiscussStatistics.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(DiscussStatistics entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除评论统计，机构用
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
