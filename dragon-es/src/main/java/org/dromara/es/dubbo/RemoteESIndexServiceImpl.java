package org.dromara.es.dubbo;

import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.common.core.domain.R;
import org.dromara.es.api.RemoteESIndexService;
import org.dromara.es.api.domain.CourseBaseDto;
import org.dromara.es.controller.CourseController;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.esmapper.CourseBaseMapper;
import org.dromara.es.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DubboService
@Service
public class RemoteESIndexServiceImpl implements RemoteESIndexService {

    @Autowired
    CourseController courseController;
    @Autowired
    CourseService courseService;
    @Resource
    private CourseBaseMapper courseBaseMapper;

    @Override
    public Boolean saveData2Index(CourseBaseDto dto) {
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(dto, courseBase);
        return courseController.save(courseBase);
    }

    @Override
    public boolean setCourseHot(Long id, boolean isHot) {
        return courseService.setCourseHot(id, isHot);
    }

    @Override
    public R<String> updateStar(Long courseId, BigDecimal star) {
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null){
            return R.fail("课程不存在");
        }
        courseBase.setStar(star);
        boolean b = courseBaseMapper.updateById(courseBase) > 0;
        if (b){
            return R.ok("更新star in ES成功");
        }else{
            return R.fail("更新star in ES失败");
        }
    }

    @Override
    public R<String> updateStarBatch(Map<Long, BigDecimal> courseStars) {
        List<Long> courseIds = new ArrayList<>(courseStars.keySet());
        //select
        List<CourseBase> courseBases = courseBaseMapper.selectBatchIds(courseIds);
        int setNum = 0;
        for (CourseBase courseBase : courseBases) {
            if (courseStars.containsKey(courseBase.getId())){
                courseBase.setStar(courseStars.get(courseBase.getId()));
                setNum ++;
            }else {
                break;
            }
        }
        if (setNum != courseIds.size() || setNum != courseStars.size()){
            return R.fail("需要更新的course中有异常，ES查询数量有差异");
        }
        Integer updated = courseBaseMapper.updateBatchByIds(courseBases);
        if (updated != courseStars.size()){
            return R.fail("ES已部分更新，部分课程未知原因无法更新");
        }else {
            return R.ok("课程star已全部在ES中更新完成");
        }
    }
}
