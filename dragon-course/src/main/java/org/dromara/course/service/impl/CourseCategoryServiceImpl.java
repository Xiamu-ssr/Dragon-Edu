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
import org.dromara.course.domain.bo.CourseCategoryBo;
import org.dromara.course.domain.vo.CourseCategoryVo;
import org.dromara.course.domain.CourseCategory;
import org.dromara.course.mapper.CourseCategoryMapper;
import org.dromara.course.service.CourseCategoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 分类管理Service业务层处理
 *
 * @author Xiamu
 * @date 2024-03-29
 */
@RequiredArgsConstructor
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    private final CourseCategoryMapper baseMapper;

    /**
     * 查询分类管理
     */
    @Override
    public CourseCategoryVo queryById(String id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询分类管理列表
     */
    @Override
    public List<CourseCategoryVo> queryPageList(CourseCategoryBo bo) {
        LambdaQueryWrapper<CourseCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectCategoryList(lqw);
    }

    /**
     * 查询分类管理列表
     */
    @Override
    public List<CourseCategoryVo> queryList(CourseCategoryBo bo) {
        LambdaQueryWrapper<CourseCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CourseCategory> buildQueryWrapper(CourseCategoryBo bo) {
        LambdaQueryWrapper<CourseCategory> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), CourseCategory::getName, bo.getName());
        lqw.orderByAsc(CourseCategory::getParentid);
        lqw.orderByAsc(CourseCategory::getOrderby);
        lqw.orderByAsc(CourseCategory::getId);
        return lqw;
    }

    /**
     * 新增分类管理
     */
    @Override
    public Boolean insertByBo(CourseCategoryBo bo) {
        CourseCategory add = MapstructUtils.convert(bo, CourseCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改分类管理
     */
    @Override
    public Boolean updateByBo(CourseCategoryBo bo) {
        CourseCategory update = MapstructUtils.convert(bo, CourseCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CourseCategory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除分类管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
