package org.dromara.es.service.impl;

import org.dromara.common.core.utils.StringUtils;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.biz.OrderByParam;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.easyes.core.conditions.update.LambdaEsUpdateWrapper;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.domain.bo.CourseQueryBo;
import org.dromara.es.domain.bo.CourseQueryBoostBo;
import org.dromara.es.esmapper.CourseBaseMapper;
import org.dromara.es.service.CourseService;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
            //付费规则全选时，参数为空，不筛选
            .eq(bo.getIsHot() != null, CourseBase::getIsHot, bo.getIsHot(), CourseQueryBoostBo.isHot)
            //搜索框名称为空时，不筛选
            .match(StringUtils.isNotEmpty(bo.getName()), CourseBase::getName, bo.getName(), CourseQueryBoostBo.name);

        if (bo.getOrderByParam()!=null && bo.getOrderByParam().getOrder()!=null && bo.getOrderByParam().getSort()!=null){
            //排序选择综合时，OrderByParam为空，不参与排序
            queryWrapper.orderBy(bo.getOrderByParam());
        }

        //热门课程置顶或者排在更前面
        //创建
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
            new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                QueryBuilders.termQuery("isHot", true),
                ScoreFunctionBuilders.weightFactorFunction(2.0f)
            )
        };
        SearchSourceBuilder searchSourceBuilder = courseBaseMapper.getSearchSourceBuilder(queryWrapper);
        // 创建 function_score 查询
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
            searchSourceBuilder.query(), // 基本查询
            filterFunctionBuilders // 权重函数
        ).boostMode(CombineFunction.MULTIPLY); // 设置得分模式，例如 MULTIPLY

        //分页查询
        return courseBaseMapper.pageQuery(queryWrapper, pageNum, pageSize);
    }

    @Override
    public boolean setCourseHot(Long id, boolean isHot) {
        CourseBase courseBase = courseBaseMapper.selectById(id);
        courseBase.setIsHot(isHot);
        return courseBaseMapper.updateById(courseBase) > 0;
    }
}
