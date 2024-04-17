package org.dromara.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.course.domain.bo.TeachplanBo;
import org.dromara.course.domain.vo.TeachplanVo;
import org.dromara.course.domain.Teachplan;
import org.dromara.course.mapper.TeachplanMapper;
import org.dromara.course.service.TeachplanService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程计划Service业务层处理
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@RequiredArgsConstructor
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {

    private final TeachplanMapper baseMapper;

    /**
     * 查询课程计划
     */
    @Override
    public TeachplanVo queryById(String id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询课程计划列表
     */
    @Override
    public TableDataInfo<TeachplanVo> queryPageList(TeachplanBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Teachplan> lqw = buildQueryWrapper(bo);
        Page<TeachplanVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程计划列表
     */
    @Override
    public List<TeachplanVo> queryList(TeachplanBo bo) {
        LambdaQueryWrapper<Teachplan> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Teachplan> buildQueryWrapper(TeachplanBo bo) {
        LambdaQueryWrapper<Teachplan> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPname()), Teachplan::getPname, bo.getPname());
        lqw.eq(StringUtils.isNotBlank(bo.getParentid()), Teachplan::getParentid, bo.getParentid());
        lqw.eq(bo.getGrade() != null, Teachplan::getGrade, bo.getGrade());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), Teachplan::getDescription, bo.getDescription());
        lqw.eq(bo.getOrderby() != null, Teachplan::getOrderby, bo.getOrderby());
        lqw.eq(bo.getCourseId() != null, Teachplan::getCourseId, bo.getCourseId());
        lqw.eq(bo.getMediaId() != null, Teachplan::getMediaId, bo.getMediaId());
        lqw.eq(bo.getIsPreview() != null, Teachplan::isPreview, bo.getIsPreview());
        return lqw;
    }

    /**
     * 新增课程计划
     */
    @Override
    public Boolean insertByBo(TeachplanBo bo) {
        Teachplan add = MapstructUtils.convert(bo, Teachplan.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程计划
     */
    @Override
    public Boolean updateByBo(TeachplanBo bo) {
        Teachplan update = MapstructUtils.convert(bo, Teachplan.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Teachplan entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除课程计划
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
