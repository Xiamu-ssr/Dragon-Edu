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
import org.dromara.course.domain.bo.TeacherBo;
import org.dromara.course.domain.vo.TeacherVo;
import org.dromara.course.domain.Teacher;
import org.dromara.course.mapper.TeacherMapper;
import org.dromara.course.service.TeacherService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 教师管理Service业务层处理
 *
 * @author Xiamu
 * @date 2024-03-22
 */
@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper baseMapper;

    /**
     * 查询教师管理
     */
    @Override
    public TeacherVo queryById(String id, Long CompanyId){
        TeacherVo teacherVo = baseMapper.selectVoById(id);
        return CompanyId.equals(teacherVo.getCompanyId()) ? teacherVo : null;
    }

    /**
     * 查询教师管理列表
     */
    @Override
    public TableDataInfo<TeacherVo> queryPageList(TeacherBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Teacher> lqw = buildQueryWrapper(bo);
        //System.out.println(lqw.toString());
        //System.out.println(bo.getCompanyId());
        Page<TeacherVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询教师管理列表
     */
    @Override
    public List<TeacherVo> queryList(TeacherBo bo) {
        LambdaQueryWrapper<Teacher> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Teacher> buildQueryWrapper(TeacherBo bo) {
        LambdaQueryWrapper<Teacher> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCompanyId() != null, Teacher::getCompanyId, bo.getCompanyId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), Teacher::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getPosition()), Teacher::getPosition, bo.getPosition());
        lqw.eq(StringUtils.isNotBlank(bo.getIntroduction()), Teacher::getIntroduction, bo.getIntroduction());
        lqw.eq(StringUtils.isNotBlank(bo.getPhotograph()), Teacher::getPhotograph, bo.getPhotograph());
        return lqw;
    }

    /**
     * 新增教师管理
     */
    @Override
    public Boolean insertByBo(TeacherBo bo) {
        Teacher add = MapstructUtils.convert(bo, Teacher.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改教师管理
     */
    @Override
    public Boolean updateByBo(TeacherBo bo) {
        Teacher update = MapstructUtils.convert(bo, Teacher.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Teacher entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除教师管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
