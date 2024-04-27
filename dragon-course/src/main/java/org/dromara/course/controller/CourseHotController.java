package org.dromara.course.controller;

import cn.hutool.http.HttpStatus;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.course.domain.CourseAll;
import org.dromara.course.domain.bo.CourseBaseBo;
import org.dromara.course.domain.bo.CoursePublishBo;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.domain.vo.CoursePublishVo;
import org.dromara.course.service.CourseHotService;
import org.dromara.course.service.CoursePublishService;
import org.dromara.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 热门课程
 *
 * @author mumu
 * @date 2024/04/24
 */
@RestController
@RequestMapping("/hot")
public class CourseHotController {

    @Autowired
    CourseHotService courseHotService;
    @Autowired
    CoursePublishService coursePublishService;


    /**
     * 测试
     *
     * @return {@link R}<{@link Void}>
     */
    @GetMapping("/test")
    public R<Void> test(){
        boolean b = courseHotService.test();
        return R.ok();
    }

    /**
     * 获取热门课程列表
     * <br/>
     * 只有base信息即可
     *
     * @return {@link R}<{@link Void}>
     */
    @Deprecated(since = "获取热门课程从es获取而不是redis，虽然redis也已经实现了分页")
    @GetMapping("/list")
    public TableDataInfo<CourseBaseVo> baseList(PageQuery pageQuery) {
        if (pageQuery.getPageNum() < 1){
            return null;
        }
        return courseHotService.basePageList(pageQuery);
    }

    /**
     * 获取某一热门课程具体信息
     *
     * @param id 身份证件
     * @return {@link TableDataInfo}<{@link CourseBaseVo}>
     */
    @GetMapping("/{id}")
    public R<CourseAll> getInfo(@PathVariable Long id) {
        CourseAll courseAll = courseHotService.queryById(id);
        if (courseAll != null){
            return R.ok(courseAll);
        }else {
            return R.fail("此热门课程不存在");
        }
    }

    /**
     * 获取热门课程ids列表
     *
     * @return {@link R}<{@link Void}>
     */
    @GetMapping("/ids")
    public R<List<Long>> idsList() {
        return R.ok(courseHotService.idsList());
    }


    /**
     * 提拔热门课程
     * <br/>
     * 用于热门管理，所以需要同时更新es
     * @return {@link R}<{@link Void}>
     */
    @GetMapping("/add/{id}")
    public R<Void> add(@PathVariable("id") Long id) {
        Boolean b = courseHotService.add(id);
        if (b){
            return R.ok("添加热门课程成功");
        }else {
            return R.fail("添加热门课程失败");
        }
    }


    /**
     * 更新热门课程内容
     * <br/>
     * 用于重新发布课程，更新内容无hot，不需要更新es
     * @return {@link R}<{@link Void}>
     */
    @PutMapping("/update/{id}")
    public R<Void> update(@PathVariable("id") Long id) {
        Boolean b = courseHotService.update(id);
        if (b){
            return R.ok("更新热门课程成功");
        }else {
            return R.fail("更新热门课程失败");
        }
    }

    /**
     * 罢免热门课程
     * <br/>
     * 用于热门管理，所以需要同时更新es
     * @return {@link R}<{@link Void}>
     */
    @GetMapping("/del/{id}")
    public R<Void> del(@PathVariable("id") Long id) {
        Boolean b = courseHotService.del(id);
        if (b){
            return R.ok("罢免热门课程成功");
        }else {
            return R.fail("罢免热门课程失败");
        }
    }


    /**
     * 查询非热门课程发布列表
     * <br/>
     * 非热门课程，仅需base信息即可
     */
    @Deprecated(since = "获取热门课程从es模块获取而不是course，和热门课程达到统一")
    @GetMapping("/list/notHot")
    public TableDataInfo<CourseBaseVo> listNotHot(CoursePublishBo bo, PageQuery pageQuery) {
        return coursePublishService.queryPageList(bo, pageQuery);
    }

    /**
     * 刷新redis缓存
     * <br/>
     * 热门课程由es查询，可能存在中途redis被删除等故障。需要根据es提供的ids记录，刷新热门课程。
     * <br/>
     *
     */
    //:todo
    @GetMapping("/reload")
    public R<Boolean> reload() {
        return null;
    }

}
