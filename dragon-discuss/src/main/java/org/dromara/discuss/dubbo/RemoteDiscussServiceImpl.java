package org.dromara.discuss.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.linpeilie.annotations.AutoMapMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.course.api.domain.CourseBase;
import org.dromara.discuss.api.RemoteDiscussService;
import org.dromara.discuss.api.domain.BestCourseDto;
import org.dromara.discuss.api.domain.RemoteDiscussCourseBaseDto;
import org.dromara.discuss.domain.DiscussStatistics;
import org.dromara.discuss.mapper.DiscussStatisticsMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@DubboService
@Slf4j
public class RemoteDiscussServiceImpl implements RemoteDiscussService {

    @Autowired
    DiscussStatisticsMapper statisticsMapper;

    @Override
    public boolean createNewStatistics(RemoteDiscussCourseBaseDto dto) {
        LambdaQueryWrapper<DiscussStatistics> queryWrapper = new LambdaQueryWrapper<DiscussStatistics>()
            .eq(DiscussStatistics::getCourseId, dto.getId());
        //if this course's statistics exits?
        //if not , create base one
        boolean exists = statisticsMapper.exists(queryWrapper);
        if (!exists){
            DiscussStatistics statistics = new DiscussStatistics();
            statistics.setCourseId(dto.getId());
            statistics.setCourseName(dto.getName());
            statistics.setPic(dto.getPic());
            statistics.setCompanyId(dto.getCompanyId());
            statistics.setDiscussCount(0L);
            statistics.setStar(new BigDecimal("5.0"));
            boolean b = statisticsMapper.insert(statistics) > 0;
            if (!b){
                log.error("初始化评论统计表失败:{}", dto);
                throw new BaseException("初始化评论统计系统失败");
            }
        }else {
            return true;
        }
        return true;
    }

    @Override
    public Integer getDiscussNum(Long company_id) {
        Long discussNum = statisticsMapper.getDiscussNum(company_id);
        return discussNum.intValue();
    }

    @Override
    public List<BestCourseDto> getBestCourse(Long company_id) {
        List<DiscussStatistics> list = statisticsMapper.selectList(new LambdaQueryWrapper<DiscussStatistics>()
            .select(DiscussStatistics::getCourseName, DiscussStatistics::getStar)
            .eq(DiscussStatistics::getCompanyId, company_id)
            .orderByDesc(DiscussStatistics::getStar)
            .last("limit 5")
        );
        return list.stream().map(discussStatistics -> {
            BestCourseDto dto = new BestCourseDto();
            BeanUtils.copyProperties(discussStatistics, dto);
            return dto;
        }).toList();
    }
}
