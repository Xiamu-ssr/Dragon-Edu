package org.dromara.course.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.course.api.RemoteCourseService;
import org.dromara.course.api.domain.CourseBase;
import org.dromara.course.domain.CoursePublish;
import org.dromara.course.domain.Teachplan;
import org.dromara.course.mapper.CourseBaseMapper;
import org.dromara.course.mapper.CoursePublishMapper;
import org.dromara.course.mapper.TeachplanMapper;
import org.dromara.course.service.CourseHotService;
import org.dromara.course.service.CoursePublishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DubboService
public class RemoteCourseServiceImpl implements RemoteCourseService {
    @Autowired
    CourseBaseMapper baseMapper;
    @Autowired
    CoursePublishMapper coursePublishMapper;
    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    CoursePublishService publishService;


    @Override
    public CourseBase getCourseBaseById(Long courseId) {
        org.dromara.course.domain.CourseBase baseInfo = publishService.getCourseBaseInfo(courseId);
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(baseInfo, courseBase);
        return courseBase;
    }

    @Override
    public Integer getCourseNum(Long companyId) {
        Long l = coursePublishMapper.selectCount(new LambdaQueryWrapper<CoursePublish>()
            .eq(CoursePublish::getCompanyId, companyId));
        return l.intValue();
    }

    @Override
    public List<Integer> getCourseNumAndTeachplanNum(Long companyId) {
        //query all course ids belong to company_id
        List<Long> ids = coursePublishMapper.selectObjs(new LambdaQueryWrapper<CoursePublish>()
                .select(CoursePublish::getId)
                .eq(CoursePublish::getCompanyId, companyId)
            ).stream()
            .map(id -> (Long) id)
            .toList();
        //teachplan number
        Long count = 0L;
        if (!ids.isEmpty()){
            count = teachplanMapper.selectCount(new LambdaQueryWrapper<Teachplan>()
                .in(Teachplan::getCourseId, ids)
            );
        }
        //return
        ArrayList<Integer> res = new ArrayList<>();
        res.add(ids.size());
        res.add(count.intValue());
        return res;
    }

    @Override
    public Map<Long, String> getCoursePics(List<Long> ids) {
        List<org.dromara.course.domain.CourseBase> bases = baseMapper.selectList(new LambdaQueryWrapper<org.dromara.course.domain.CourseBase>()
            .select(org.dromara.course.domain.CourseBase::getId, org.dromara.course.domain.CourseBase::getPic)
            .in(org.dromara.course.domain.CourseBase::getId, ids));
        return bases.stream().collect(Collectors.toMap(org.dromara.course.domain.CourseBase::getId, org.dromara.course.domain.CourseBase::getPic));
    }
}
