package org.dromara.course.dubbo;

import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.course.domain.CourseAll;
import org.dromara.course.domain.CourseBase;
import org.dromara.course.domain.Teachplan;
import org.dromara.course.mapper.TeachplanMapper;
import org.dromara.course.service.CourseOpenService;
import org.dromara.course.api.RemoteTeachplanService;
import org.dromara.course.service.CoursePublishService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@DubboService
public class RemoteTeachplanServiceImpl implements RemoteTeachplanService {
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
}
