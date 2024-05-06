package org.dromara.course.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.course.domain.CourseAll;
import org.dromara.course.domain.CourseBase;
import org.dromara.course.domain.CoursePublish;
import org.dromara.course.domain.Teachplan;
import org.dromara.course.mapper.CoursePublishMapper;
import org.dromara.course.mapper.TeachplanMapper;
import org.dromara.course.service.CourseOpenService;
import org.dromara.course.api.RemoteTeachplanService;
import org.dromara.course.service.CoursePublishService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@DubboService
public class RemoteTeachplanServiceImpl implements RemoteTeachplanService {
    @Autowired
    CoursePublishMapper coursePublishMapper;
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    CoursePublishService publishService;

    @Override
    public Map<String, Object> checkAndGetMedia(Long courseId, String teachplanId) {
        Map<String, Object> res = new HashMap<>();

        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if (teachplan != null && Objects.equals(teachplan.getCourseId(), courseId)){
            CourseBase baseInfo = publishService.getCourseBaseInfo(courseId);
            if (baseInfo != null){
                res.put("mediaId", teachplan.getMediaId());
                res.put("isCharge", baseInfo.getCharge());
                res.put("isPreview", teachplan.isPreview());
                return res;
            }
        }
        return null;
    }

    @Override
    public Integer getTeachplanNum(Long companyId) {
        //query all course ids belong to company_id
        List<Long> ids = coursePublishMapper.selectObjs(new LambdaQueryWrapper<CoursePublish>()
                .select(CoursePublish::getId)
                .eq(CoursePublish::getCompanyId, companyId)
            ).stream()
            .map(id -> (Long) id)
            .toList();
        //
        Long count = teachplanMapper.selectCount(new LambdaQueryWrapper<Teachplan>()
            .in(Teachplan::getCourseId, ids)
        );
        return count.intValue();
    }
}
