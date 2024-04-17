package org.dromara.course.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.course.domain.bo.CourseBaseBo;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.domain.CourseBase;
import org.dromara.course.mapper.CourseBaseMapper;
import org.dromara.course.service.CourseBaseService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程管理Service业务层处理
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@RequiredArgsConstructor
@Service
public class CourseBaseServiceImpl implements CourseBaseService {

    private final CourseBaseMapper baseMapper;

    /**
     * 查询课程管理
     */
    @Override
    public CourseBaseVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询课程管理列表
     */
    @Override
    public TableDataInfo<CourseBaseVo> queryPageList(CourseBaseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CourseBase> lqw = buildQueryWrapper(bo);
        Page<CourseBaseVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程管理列表
     */
    @Override
    public List<CourseBaseVo> queryList(CourseBaseBo bo) {
        LambdaQueryWrapper<CourseBase> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CourseBase> buildQueryWrapper(CourseBaseBo bo) {
        LambdaQueryWrapper<CourseBase> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCompanyId() != null, CourseBase::getCompanyId, bo.getCompanyId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), CourseBase::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getMt()), CourseBase::getMt, bo.getMt());
        lqw.eq(StringUtils.isNotBlank(bo.getSt()), CourseBase::getSt, bo.getSt());
        lqw.eq(StringUtils.isNotBlank(bo.getPic()), CourseBase::getPic, bo.getPic());
        lqw.eq(bo.getCharge() != null, CourseBase::getCharge, bo.getCharge());
        lqw.eq(bo.getOriginalPrice() != null, CourseBase::getOriginalPrice, bo.getOriginalPrice());
        lqw.eq(bo.getPrice() != null, CourseBase::getPrice, bo.getPrice());
        lqw.eq(bo.getStar() != null, CourseBase::getStar, bo.getStar());
        lqw.eq(bo.getStatus() != null, CourseBase::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getMind()), CourseBase::getMind, bo.getMind());
        return lqw;
    }

    /**
     * 新增课程管理
     */
    @Override
    public Boolean insertByBo(CourseBaseBo bo) {
        CourseBase add = MapstructUtils.convert(bo, CourseBase.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程管理
     */
    @Override
    public Boolean updateByBo(CourseBaseBo bo) {
        CourseBase update = MapstructUtils.convert(bo, CourseBase.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CourseBase entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除课程管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
