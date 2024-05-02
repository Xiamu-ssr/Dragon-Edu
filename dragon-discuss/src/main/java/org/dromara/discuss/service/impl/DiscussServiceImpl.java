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
import org.dromara.discuss.domain.bo.DiscussBo;
import org.dromara.discuss.domain.vo.DiscussVo;
import org.dromara.discuss.domain.Discuss;
import org.dromara.discuss.mapper.DiscussMapper;
import org.dromara.discuss.service.DiscussService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程评论Service业务层处理
 *
 * @author LionLi
 * @date 2024-05-02
 */
@RequiredArgsConstructor
@Service
public class DiscussServiceImpl implements DiscussService {

    private final DiscussMapper baseMapper;

    /**
     * 查询课程评论
     */
    @Override
    public DiscussVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询课程评论列表
     */
    @Override
    public TableDataInfo<DiscussVo> queryPageList(DiscussBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Discuss> lqw = buildQueryWrapper(bo);
        Page<DiscussVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程评论列表
     */
    @Override
    public List<DiscussVo> queryList(DiscussBo bo) {
        LambdaQueryWrapper<Discuss> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Discuss> buildQueryWrapper(DiscussBo bo) {
        LambdaQueryWrapper<Discuss> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCourseId() != null, Discuss::getCourseId, bo.getCourseId());
        lqw.like(StringUtils.isNotBlank(bo.getCourseName()), Discuss::getCourseName, bo.getCourseName());
        lqw.eq(bo.getCompanyId() != null, Discuss::getCompanyId, bo.getCompanyId());
        lqw.eq(bo.getUserId() != null, Discuss::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), Discuss::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), Discuss::getAvatar, bo.getAvatar());
        lqw.eq(bo.getLearnTime() != null, Discuss::getLearnTime, bo.getLearnTime());
        lqw.eq(bo.getStar() != null, Discuss::getStar, bo.getStar());
        lqw.eq(bo.getStatus() != null, Discuss::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增课程评论
     */
    @Override
    public Boolean insertByBo(DiscussBo bo) {
        Discuss add = MapstructUtils.convert(bo, Discuss.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程评论
     */
    @Override
    public Boolean updateByBo(DiscussBo bo) {
        Discuss update = MapstructUtils.convert(bo, Discuss.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Discuss entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除课程评论
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
