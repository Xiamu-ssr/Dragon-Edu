package org.dromara.es.dubbo;

import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.es.api.RemoteESIndexService;
import org.dromara.es.api.domain.CourseBaseDto;
import org.dromara.es.controller.CourseController;
import org.dromara.es.domain.CourseBase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class RemoteESIndexServiceImpl implements RemoteESIndexService {

    @Autowired
    CourseController courseController;

    @Override
    public Boolean saveData2Index(CourseBaseDto dto) {
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(dto, courseBase);
        return courseController.save(courseBase);
    }
}
