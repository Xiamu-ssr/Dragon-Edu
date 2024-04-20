package org.dromara.course.service;

import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.course.domain.CourseAll;
import org.dromara.course.domain.vo.CourseBaseVo;

import java.util.List;
import java.util.stream.Stream;

public interface CourseHotService {

    boolean test();

    /**
     * 获取热门课程列表
     * <BR/>
     * 只包含base信息
     *
     * @return {@link List}<{@link CourseBaseVo}>
     */
    TableDataInfo<CourseBaseVo> basePageList(PageQuery pageQuery);

    /**
     * 获取某一热门课程具体信息
     *
     * @param id 身份证件
     * @return {@link TableDataInfo}<{@link CourseBaseVo}>
     */
    CourseAll queryById(Long id);


    /**
     * 获取热门课程ids列表
     *
     * @return {@link R}<{@link Void}>
     */
    List<Long> idsList();

    /**
     * 添加热门课程
     *
     * @param id 身份证件
     * @return {@link Boolean}
     */
    Boolean add(Long id);

    /**
     * 更新门课程
     *
     * @param id 身份证件
     * @return {@link Boolean}
     */
    Boolean update(Long id);


    ///**
    // * 添加或者更新门课程
    // *
    // * @param id 身份证件
    // * @return {@link Boolean}
    // */
    //Boolean save(Long id);



    /**
     * 罢免热门课程
     *
     * @param id 身份证件
     * @return {@link Boolean}
     */
    Boolean del(Long id);


}
