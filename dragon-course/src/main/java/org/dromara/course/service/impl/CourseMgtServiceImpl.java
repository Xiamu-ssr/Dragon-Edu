package org.dromara.course.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.course.domain.CourseBase;
import org.dromara.course.domain.CourseExtra;
import org.dromara.course.domain.Teacher;
import org.dromara.course.domain.Teachplan;
import org.dromara.course.domain.bo.CourseBaseBo;
import org.dromara.course.domain.bo.CourseMgt1Bo;
import org.dromara.course.domain.bo.CourseMgt2Bo;
import org.dromara.course.domain.bo.CourseMgt3Bo;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.domain.vo.CourseMgt1Vo;
import org.dromara.course.domain.vo.CourseMgt2Vo;
import org.dromara.course.enums.CourseStatusEnum;
import org.dromara.course.mapper.CourseBaseMapper;
import org.dromara.course.mapper.CourseExtraMapper;
import org.dromara.course.mapper.TeacherMapper;
import org.dromara.course.mapper.TeachplanMapper;
import org.dromara.course.service.CourseMgtService;
import org.dromara.course.service.TeachplanService;
import org.dromara.es.api.RemoteMediaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CourseMgtServiceImpl implements CourseMgtService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Autowired
    private CourseExtraMapper courseExtraMapper;
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeachplanService teachplanService;

    @DubboReference
    private RemoteMediaService remoteMediaService;

    /**
     * 查询课程
     */
    @Override
    public CourseBaseVo queryById(Long id){
        return courseBaseMapper.selectVoById(id);
    }

    /**
     * 查询课程列表
     */
    @Override
    public TableDataInfo<CourseBaseVo> queryPageList(CourseBaseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CourseBase> lqw = buildQueryWrapper(bo);
        Page<CourseBaseVo> result = courseBaseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程列表
     */
    @Override
    public List<CourseBaseVo> queryList(CourseBaseBo bo) {
        LambdaQueryWrapper<CourseBase> lqw = buildQueryWrapper(bo);
        return courseBaseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CourseBase> buildQueryWrapper(CourseBaseBo bo) {
        LambdaQueryWrapper<CourseBase> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCompanyId() != null, CourseBase::getCompanyId, bo.getCompanyId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), CourseBase::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getMt()), CourseBase::getMt, bo.getMt());
        lqw.eq(StringUtils.isNotBlank(bo.getSt()), CourseBase::getSt, bo.getSt());
        lqw.eq(StringUtils.isNotBlank(bo.getPic()), CourseBase::getPic, bo.getPic());
        lqw.eq(bo.getStatus()!=null, CourseBase::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getMind()), CourseBase::getMind, bo.getMind());
        return lqw;
    }

    @Override
    public CourseMgt1Vo getOne(Long id) {
        CourseBase courseBase = courseBaseMapper.selectById(id);
        CourseExtra courseExtra = courseExtraMapper.selectById(id);
        CourseMgt1Vo courseMgt1Vo = new CourseMgt1Vo();
        BeanUtils.copyProperties(courseBase, courseMgt1Vo);
        BeanUtils.copyProperties(courseExtra, courseMgt1Vo);
        courseMgt1Vo.setTags(courseExtra.getTags().split(","));
        return courseMgt1Vo;
    }

    @Override
    public List<CourseMgt2Vo> getTwo(Long id) {
        List<Teachplan> teachplans = teachplanMapper.selectList(new LambdaQueryWrapper<Teachplan>().eq(Teachplan::getCourseId, id));
        //:fixme
        // 从media服务中去查询mediaName，因为teachplanMapper中的mediaName不一定实时。并且目前不打算在media同步mediaName
        //获取所有需要查询的mediaIds
        List<String> mediaIds = teachplans.stream()
            .filter(teachplan -> StringUtils.isNotEmpty(teachplan.getMediaId()))
            .map(Teachplan::getMediaId).toList();
        //查询<mediaId, mediaName>的映射
        if(!mediaIds.isEmpty()){
            Map<String, String> map = remoteMediaService.selectMediaNames(mediaIds);
            //修改课程计划
            teachplans.forEach(teachplan -> {
                String mediaId = teachplan.getMediaId();
                if (StringUtils.isNotEmpty(mediaId)){
                    teachplan.setMediaName(map.get(mediaId));
                }
            });
        }
        //按照parentid分组
        Map<String, List<Teachplan>> listMap = teachplans.stream().collect(Collectors.groupingBy(Teachplan::getParentid));
        //处理大章节
        List<CourseMgt2Vo> largeChapters = listMap.getOrDefault("0", new ArrayList<Teachplan>()).stream()
            .map(this::convertToCourseMgt2Vo)
            .sorted(Comparator.comparingLong(CourseMgt2Vo::getOrderby))
            .toList();
        //处理小章节
        largeChapters.forEach(largeChapter->largeChapter.setChapter(
            listMap.getOrDefault(largeChapter.getId(), new ArrayList<Teachplan>()).stream()
                .map(this::convertToCourseMgt2Vo)
                .sorted(Comparator.comparingLong(CourseMgt2Vo::getOrderby))
                .toList()
        ));
        return largeChapters;
    }

    @Override
    public List<Teacher> getThree(Long id) {
        CourseExtra courseExtra = courseExtraMapper.selectById(id);
        String[] ids = courseExtra.getTeachers().split(",");
        List<Long> list = Arrays.stream(ids).map(Long::valueOf).toList();
        return teacherMapper.selectBatchIds(list);
    }

    private CourseMgt2Vo convertToCourseMgt2Vo(Teachplan teachplan){
        CourseMgt2Vo vo = new CourseMgt2Vo();
        BeanUtils.copyProperties(teachplan, vo);
        return vo;
    }

    /**
     * 新增课程
     */
    @Override
    @Transactional
    public Long saveOrUpdateOne(CourseMgt1Bo bo) {
        //更新course_base
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(bo, courseBase);
        boolean flag1 = courseBaseMapper.insertOrUpdate(courseBase);
        //更新course_extra
        CourseExtra courseExtra = new CourseExtra();
        BeanUtils.copyProperties(bo, courseExtra);
        courseExtra.setId(courseBase.getId());
        boolean flag2 = courseExtraMapper.insertOrUpdate(courseExtra);

        if (flag1 && flag2) {
            return courseBase.getId();
        }else {
            return -1L;
        }
    }

    @Override
    public Boolean saveOrUpdateTwo(List<CourseMgt2Bo> list) {
        List<Teachplan> teachplans = new ArrayList<Teachplan>();
        list.forEach(courseTeachplanBo -> {
            //大节
            Teachplan teachplanLarge = new Teachplan();
            BeanUtils.copyProperties(courseTeachplanBo, teachplanLarge);
            if (teachplanLarge.getId() == null){
                teachplanLarge.setId(UUID.randomUUID().toString());
            }
            teachplanLarge.setParentid("0");
            teachplans.add(teachplanLarge);
            //大节的小节
            courseTeachplanBo.getChapter().forEach(bo->{
                Teachplan teachplanSmall = new Teachplan();
                BeanUtils.copyProperties(bo, teachplanSmall);
                if (teachplanSmall.getId() == null){
                    teachplanSmall.setId(UUID.randomUUID().toString());
                }
                teachplanSmall.setParentid(teachplanLarge.getId());
                teachplans.add(teachplanSmall);
            });
        });
        teachplanMapper.delete(new LambdaQueryWrapper<Teachplan>().eq(Teachplan::getCourseId, teachplans.get(0).getCourseId()));
        return teachplanService.saveOrUpdateBatch(teachplans);
    }

    @Override
    public Boolean saveOrUpdateThree(CourseMgt3Bo bo) {
        LambdaUpdateWrapper<CourseExtra> updateWrapper = new LambdaUpdateWrapper<CourseExtra>()
            .eq(CourseExtra::getId, bo.getCourseId())
            .set(CourseExtra::getTeachers, bo.getTeacher());
        return courseExtraMapper.update(updateWrapper) > 0;
    }


    /**
     * 修改课程
     */
    @Override
    public Boolean updateByBo(CourseBaseBo bo) {
        CourseBase update = MapstructUtils.convert(bo, CourseBase.class);
        validEntityBeforeSave(update);
        return courseBaseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CourseBase entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除课程
     */
    @Override
    @Transactional
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        boolean b1 = courseBaseMapper.deleteBatchIds(ids) > 0;
        boolean b2 = courseExtraMapper.deleteBatchIds(ids) > 0;
        boolean b3 = teachplanMapper.delete(new LambdaQueryWrapper<Teachplan>().in(Teachplan::getCourseId, ids)) > 0;
        return  b1 && b2 && b3;
    }

    @Override
    public Boolean publish(Long id) {
        LambdaUpdateWrapper<CourseBase> updateWrapper = new LambdaUpdateWrapper<CourseBase>()
            .eq(CourseBase::getId, id)
            .set(CourseBase::getStatus, CourseStatusEnum.UNDER_REVIEW.getValue());
        return courseBaseMapper.update(updateWrapper) > 0;
    }

    @Override
    public Boolean offShelf(Long id) {
        LambdaUpdateWrapper<CourseBase> updateWrapper = new LambdaUpdateWrapper<CourseBase>()
            .eq(CourseBase::getId, id)
            .set(CourseBase::getStatus, CourseStatusEnum.UNPUBLISHED.getValue());
        return courseBaseMapper.update(updateWrapper) > 0;
    }
}
