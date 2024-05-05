package org.dromara.discuss.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.course.api.RemoteCourseService;
import org.dromara.course.api.domain.CourseBase;
import org.dromara.discuss.domain.DiscussStatistics;
import org.dromara.discuss.enums.DiscussStatusEnum;
import org.dromara.discuss.mapper.DiscussStatisticsMapper;
import org.dromara.learn.api.RemoteScheduleService;
import org.springframework.stereotype.Service;
import org.dromara.discuss.domain.bo.DiscussBo;
import org.dromara.discuss.domain.vo.DiscussVo;
import org.dromara.discuss.domain.Discuss;
import org.dromara.discuss.mapper.DiscussMapper;
import org.dromara.discuss.service.DiscussService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程评论Service业务层处理
 *
 * @author LionLi
 * @date 2024-05-03
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class DiscussServiceImpl implements DiscussService {

    private final DiscussMapper discussMapper;

    private final DiscussStatisticsMapper statisticsMapper;

    @DubboReference
    RemoteScheduleService scheduleService;
    @DubboReference
    RemoteCourseService courseService;

    /**
     * 查询课程评论
     */
    @Override
    public DiscussVo queryById(Long id){
        return discussMapper.selectVoById(id);
    }

    /**
     * 查询课程评论列表
     */
    @Override
    public TableDataInfo<DiscussVo> queryPageList(DiscussBo bo, PageQuery pageQuery) {
        if(bo.getCourseId() == null){
            throw new BaseException("未指定课程，无法查询评论信息");
        }
        LambdaQueryWrapper<Discuss> lqw = buildQueryWrapper(bo);
        Page<DiscussVo> result = discussMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程评论列表
     */
    @Override
    public List<DiscussVo> queryList(DiscussBo bo) {
        LambdaQueryWrapper<Discuss> lqw = buildQueryWrapper(bo);
        return discussMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Discuss> buildQueryWrapper(DiscussBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Discuss> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCourseId() != null, Discuss::getCourseId, bo.getCourseId());
        lqw.like(StringUtils.isNotBlank(bo.getCourseName()), Discuss::getCourseName, bo.getCourseName());
        lqw.eq(bo.getCompanyId() != null, Discuss::getCompanyId, bo.getCompanyId());
        lqw.eq(bo.getUserId() != null, Discuss::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), Discuss::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), Discuss::getAvatar, bo.getAvatar());
        lqw.eq(bo.getLearnTime() != null, Discuss::getLearnTime, bo.getLearnTime());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), Discuss::getContent, bo.getContent());
        lqw.eq(bo.getStatus() != null, Discuss::getStatus, bo.getStatus());
        lqw.between(params.get("beginStar") != null && params.get("endStar") != null,
            Discuss::getStar ,params.get("beginStar"), params.get("endStar"));
        //default, order by create time
        lqw.orderByDesc(Discuss::getCreateTime);
        return lqw;
    }

    /**
     * 新增课程评论
     */
    @Override
    @Transactional
    public Boolean insertByBo(DiscussBo bo) {
        Discuss add = MapstructUtils.convert(bo, Discuss.class);
        validEntityBeforeSave(add);
        //set discuss status
        add.setStatus((long)DiscussStatusEnum.SUCCESS.getValue());
        //get user learning Time
        long learnTime = scheduleService.userLearnTime(add.getUserId(), add.getCourseId());
        add.setLearnTime(learnTime);
        boolean b1 = discussMapper.insert(add) > 0;
        //then update discuss statistics
        boolean b2 = addDiscussToStatistics(add);
        return b1 && b2;
    }

    /**
     * 修改课程评论
     */
    @Override
    public Boolean updateByBo(DiscussBo bo) {
        Discuss update = MapstructUtils.convert(bo, Discuss.class);
        validEntityBeforeSave(update);
        return discussMapper.updateById(update) > 0;
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
        return discussMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional
    public boolean addDiscussToStatistics(Discuss discuss){
        LambdaQueryWrapper<DiscussStatistics> queryWrapper = new LambdaQueryWrapper<DiscussStatistics>()
            .eq(DiscussStatistics::getCourseId, discuss.getCourseId());
        //if this course's statistics exits?
        //if not , create base one
        boolean exists = statisticsMapper.exists(queryWrapper);
        if (!exists){
            CourseBase courseBase = courseService.getCourseBaseById(discuss.getCourseId());
            DiscussStatistics statistics = new DiscussStatistics();
            statistics.setCourseId(courseBase.getId());
            statistics.setCourseName(courseBase.getName());
            statistics.setPic(courseBase.getPic());
            statistics.setCompanyId(courseBase.getCompanyId());
            statistics.setDiscussCount(0L);
            statistics.setStar(new BigDecimal("5.0"));
            boolean b = statisticsMapper.insert(statistics) > 0;
            if (!b){
                log.error("初始化评论统计表失败:{}", discuss);
                throw new BaseException("初始化评论统计系统失败");
            }
        }
        //now exist , then calculate new  Star-Value
        DiscussStatistics statistics = statisticsMapper.selectOne(queryWrapper);
        BigDecimal multiply = BigDecimal.valueOf(statistics.getDiscussCount()).multiply(statistics.getStar());
        statistics.setDiscussCount(statistics.getDiscussCount() + 1);
        BigDecimal sum = multiply.add(discuss.getStar());
        BigDecimal newStar = sum.divide(BigDecimal.valueOf(statistics.getDiscussCount()), 8, RoundingMode.HALF_UP);
        statistics.setStar(newStar);
        return statisticsMapper.updateById(statistics) > 0;
    }
}
