package org.dromara.course.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.course.domain.bo.CourseExtraBo;
import org.dromara.course.domain.vo.CourseExtraVo;
import org.dromara.course.domain.CourseExtra;
import org.dromara.course.mapper.CourseExtraMapper;
import org.dromara.course.service.CourseExtraService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程额外信息Service业务层处理
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@RequiredArgsConstructor
@Service
public class CourseExtraServiceImpl implements CourseExtraService {

    private final CourseExtraMapper baseMapper;

    /**
     * 查询课程额外信息
     */
    @Override
    public CourseExtraVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询课程额外信息列表
     */
    @Override
    public TableDataInfo<CourseExtraVo> queryPageList(CourseExtraBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CourseExtra> lqw = buildQueryWrapper(bo);
        Page<CourseExtraVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询课程额外信息列表
     */
    @Override
    public List<CourseExtraVo> queryList(CourseExtraBo bo) {
        LambdaQueryWrapper<CourseExtra> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CourseExtra> buildQueryWrapper(CourseExtraBo bo) {
        LambdaQueryWrapper<CourseExtra> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), CourseExtra::getCompanyName, bo.getCompanyName());
        lqw.eq(StringUtils.isNotBlank(bo.getTags()), CourseExtra::getTags, bo.getTags());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), CourseExtra::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getTeachers()), CourseExtra::getTeachers, bo.getTeachers());
        lqw.eq(StringUtils.isNotBlank(bo.getEmail()), CourseExtra::getEmail, bo.getEmail());
        lqw.eq(StringUtils.isNotBlank(bo.getQq()), CourseExtra::getQq, bo.getQq());
        lqw.eq(StringUtils.isNotBlank(bo.getWechat()), CourseExtra::getWechat, bo.getWechat());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), CourseExtra::getPhone, bo.getPhone());
        return lqw;
    }

    /**
     * 新增课程额外信息
     */
    @Override
    public Boolean insertByBo(CourseExtraBo bo) {
        CourseExtra add = MapstructUtils.convert(bo, CourseExtra.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程额外信息
     */
    @Override
    public Boolean updateByBo(CourseExtraBo bo) {
        CourseExtra update = MapstructUtils.convert(bo, CourseExtra.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CourseExtra entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除课程额外信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
