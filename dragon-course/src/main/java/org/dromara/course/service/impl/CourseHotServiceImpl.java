package org.dromara.course.service.impl;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.core.constant.CacheNames;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.redis.utils.CourseHotUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.course.domain.*;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.mapper.*;
import org.dromara.course.service.CourseHotService;
import org.dromara.course.service.CourseMgtService;
import org.dromara.es.api.RemoteESIndexService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseHotServiceImpl implements CourseHotService {
    /**
     * 存储热门课程数据是map，cacheName = CacheNames.COURSE_HOT<BR/>
     * key是courseId，value是CourseAll，记录一个课程的完整信息
     * */

    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseExtraMapper courseExtraMapper;
    @Autowired
    CourseMgtService courseMgtService;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CoursePublishMapper coursePublishMapper;
    @DubboReference
    RemoteESIndexService remoteESIndexService;


    @Override
    public boolean test() {
        HashMap<String, String> map = new HashMap<>();
        map.put("177", "dff");
        map.put("178", "aaa");
        map.put("179", "vvbb");
        RedisUtils.setCacheMap(CacheNames.COURSE_HOT, map);
        Map<String, Object> cacheMap = RedisUtils.getCacheMap(CacheNames.COURSE_HOT);

        RedisUtils.setCacheMapValue(CacheNames.COURSE_HOT, "177", "iii");
        Map<String, Object> cacheMap1 = RedisUtils.getCacheMap(CacheNames.COURSE_HOT);

        map.replace("179", "ppp");
        RedisUtils.setCacheMap(CacheNames.COURSE_HOT, map);
        Map<String, Object> cacheMap2 = RedisUtils.getCacheMap(CacheNames.COURSE_HOT);

        RedisUtils.delCacheMapValue(CacheNames.COURSE_HOT, "178");
        Map<String, Object> cacheMap3 = RedisUtils.getCacheMap(CacheNames.COURSE_HOT);

        RedisUtils.delCacheMapValue(CacheNames.COURSE_HOT, "177");
        RedisUtils.delCacheMapValue(CacheNames.COURSE_HOT, "179");


        return false;
    }

    @Override
    public TableDataInfo<CourseBaseVo> basePageList(PageQuery pageQuery){
        //查询处于当前页的ids
        List<Long> ids = CourseHotUtils.getHotIdPageList(pageQuery.getPageNum(), pageQuery.getPageSize(), true);
        //获取数据并保持顺序
        Map<String, CourseAll> courseHotMap = RedisUtils.getCacheMapValues(CacheNames.COURSE_HOT, ids.stream().map(Object::toString).collect(Collectors.toSet()));
        List<CourseBaseVo> data = ids.stream()
            .map(Object::toString)
            .map(courseHotMap::get)
            .filter(Objects::nonNull)
            .filter(item -> item.getId() != null)
            .map(this::convertToCourseBaseVo)
            .collect(Collectors.toList());
        //组建分页结果
        TableDataInfo<CourseBaseVo> rspData = new TableDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        rspData.setRows(data);
        rspData.setTotal(RedisUtils.getMapSize(CacheNames.COURSE_HOT));
        return rspData;
    }

    @Override
    public CourseAll queryById(Long id) {
        return RedisUtils.getCacheMapValue(CacheNames.COURSE_HOT, id.toString());
    }

    @Override
    public List<Long> idsList() {
        return CourseHotUtils.getHotIdList();
    }

    @Override
    public Boolean add(Long id) {
        //已存在无法添加
        boolean b = CourseHotUtils.isHotExist(id);
        if (b){
            return false;
        }
        //不存在则提拔
        CoursePublish coursePublish = coursePublishMapper.selectById(id);
        if (coursePublish == null || coursePublish.getInfo() == null){
            return false;
        }
        String info = coursePublish.getInfo();
        CourseAll courseAll = JSON.parseObject(info, CourseAll.class);
        //存入
        RedisUtils.setCacheMapValue(CacheNames.COURSE_HOT, id.toString(), courseAll);
        //增删热门课程后需要刷新hot id list
        CourseHotUtils.reLoadHotIdList();
        //二次check
        boolean b1 = CourseHotUtils.isHotExist(id);
        //还要更新es
        boolean b2 = remoteESIndexService.setCourseHot(id, true);
        return b1 && b2;
    }

    @Override
    public Boolean update(Long id) {
        //只有存在才能更新
        boolean b = CourseHotUtils.isHotExist(id);
        if (!b){
            return false;
        }
        //存在则更新
        CoursePublish coursePublish = coursePublishMapper.selectById(id);
        if (coursePublish == null){
            return false;
        }
        String info = coursePublish.getInfo();
        CourseAll courseAll = JSON.parseObject(info, CourseAll.class);
        //存入
        RedisUtils.setCacheMapValue(CacheNames.COURSE_HOT, id.toString(), courseAll);
        //增删热门课程后需要刷新hot id list
        CourseHotUtils.reLoadHotIdList();
        //二次check
        return CourseHotUtils.isHotExist(id);
    }

    @Override
    public Boolean del(Long id) {
        //判断是否已经存在
        boolean b = CourseHotUtils.isHotExist(id);
        if (!b){
            return false;
        }
        //存在则删除
        RedisUtils.delCacheMapValue(CacheNames.COURSE_HOT, id.toString());
        //增删热门课程后需要刷新hot id list
        CourseHotUtils.reLoadHotIdList();
        boolean b1 = !CourseHotUtils.isHotExist(id);
        boolean b2 = remoteESIndexService.setCourseHot(id, false);
        return b1 && b2;
    }

    private CourseBaseVo convertToCourseBaseVo(CourseAll courseAll) {
        // 这里实现CourseAll到CourseBaseVo的转换逻辑
        CourseBaseVo vo = new CourseBaseVo();
        BeanUtils.copyProperties(courseAll, vo);
        // 添加其他需要的属性转换
        return vo;
    }


}
