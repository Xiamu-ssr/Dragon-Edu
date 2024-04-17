package org.dromara.es.service.impl;

import org.dromara.common.core.utils.StringUtils;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.biz.OrderByParam;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.domain.bo.CourseQueryBo;
import org.dromara.es.domain.bo.CourseQueryBoostBo;
import org.dromara.es.esmapper.CourseBaseMapper;
import org.dromara.es.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    CourseBaseMapper courseBaseMapper;

    @Override
    public EsPageInfo<CourseBase> pageList(CourseQueryBo bo, Integer pageNum, Integer pageSize) {
        LambdaEsQueryWrapper<CourseBase> queryWrapper = new LambdaEsQueryWrapper<>();
        queryWrapper
            //大分类选择全部时，参数为空，不筛选
            .eq(StringUtils.isNotEmpty(bo.getMt()), CourseBase::getMt, bo.getMt(), CourseQueryBoostBo.mt)
            //小分类选择全部时，参数为空，不筛选
            .eq(StringUtils.isNotEmpty(bo.getSt()), CourseBase::getSt, bo.getSt(), CourseQueryBoostBo.st)
            //付费规则全选时，参数为空，不筛选
            .eq(bo.getCharge() != null, CourseBase::getCharge, bo.getCharge(), CourseQueryBoostBo.charge)
            //搜索框名称为空时，不筛选
            .match(StringUtils.isNotEmpty(bo.getName()), CourseBase::getName, bo.getName(), CourseQueryBoostBo.name)
            //排序选择综合时，OrderByParam为空，不参与排序
            .orderBy(bo.getOrderByParam() != null, bo.getOrderByParam());

        //分页查询
        return courseBaseMapper.pageQuery(queryWrapper, pageNum, pageSize);
    }
}
