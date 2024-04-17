package org.dromara.course.service.impl;

import com.alibaba.fastjson2.JSON;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.springframework.stereotype.Service;
import org.dromara.course.domain.bo.CoursePublishBo;
import org.dromara.course.domain.vo.CoursePublishVo;
import org.dromara.course.domain.CoursePublish;
import org.dromara.course.mapper.CoursePublishMapper;
import org.dromara.course.service.CoursePublishService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 课程发布Service业务层处理
 *
 * @author LionLi
 * @date 2024-04-15
 */
@RequiredArgsConstructor
@Service
public class CoursePublishServiceImpl implements CoursePublishService {

    private final CoursePublishMapper baseMapper;

    /**
     * 查询课程发布
     */
    @Override
    public CoursePublishVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询课程发布列表
     */
    @Override
    public TableDataInfo<CourseBaseVo> queryPageList(CoursePublishBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CoursePublish> lqw = buildQueryWrapper(bo);
        Page<CoursePublishVo> resultPre = baseMapper.selectVoPage(pageQuery.build(), lqw);

        Page<CourseBaseVo> result = new Page<>();
        result.setTotal(resultPre.getTotal());
        List<CourseBaseVo> courseBaseVos = resultPre.getRecords().stream()
            .map(coursePublishVo -> JSON.parseObject(coursePublishVo.getInfo(), CourseBaseVo.class))
            .toList();
        result.setRecords(courseBaseVos);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程发布列表
     */
    @Override
    public List<CoursePublishVo> queryList(CoursePublishBo bo) {
        LambdaQueryWrapper<CoursePublish> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CoursePublish> buildQueryWrapper(CoursePublishBo bo) {
        LambdaQueryWrapper<CoursePublish> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCompanyId() != null, CoursePublish::getCompanyId, bo.getCompanyId());
        lqw.eq(StringUtils.isNotBlank(bo.getInfo()), CoursePublish::getInfo, bo.getInfo());
        return lqw;
    }

    /**
     * 新增课程发布
     */
    @Override
    public Boolean insertByBo(CoursePublishBo bo) {
        CoursePublish add = MapstructUtils.convert(bo, CoursePublish.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程发布
     */
    @Override
    public Boolean updateByBo(CoursePublishBo bo) {
        CoursePublish update = MapstructUtils.convert(bo, CoursePublish.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CoursePublish entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除课程发布
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
