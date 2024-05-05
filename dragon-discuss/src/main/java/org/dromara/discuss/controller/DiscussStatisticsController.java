package org.dromara.discuss.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.discuss.domain.vo.DiscussStatisticsVo;
import org.dromara.discuss.domain.bo.DiscussStatisticsBo;
import org.dromara.discuss.service.DiscussStatisticsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 评论统计，机构用
 * 前端访问路由地址为:/discuss/statistics
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class DiscussStatisticsController extends BaseController {

    private final DiscussStatisticsService discussStatisticsService;

    /**
     * 查询评论统计，机构用列表
     */
    @GetMapping("/list")
    public TableDataInfo<DiscussStatisticsVo> list(DiscussStatisticsBo bo, PageQuery pageQuery) {
        return discussStatisticsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出评论统计，机构用列表
     */
    @Log(title = "评论统计，机构用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(DiscussStatisticsBo bo, HttpServletResponse response) {
        List<DiscussStatisticsVo> list = discussStatisticsService.queryList(bo);
        ExcelUtil.exportExcel(list, "评论统计，机构用", DiscussStatisticsVo.class, response);
    }

    /**
     * 获取评论统计，机构用详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<DiscussStatisticsVo> getInfo(@NotNull(message = "主键不能为空")
                                          @PathVariable Long id) {
        return R.ok(discussStatisticsService.queryById(id));
    }

    /**
     * 获取某一课程评论统计
     *
     * @param courseId 课程id
     * @return {@link R}<{@link DiscussStatisticsVo}>
     */
    @GetMapping("/course/{courseId}")
    public R<DiscussStatisticsVo> getInfoByCourseId(@NotNull(message = "主键不能为空")
                                          @PathVariable Long courseId) {
        return R.ok(discussStatisticsService.queryByCourseId(courseId));
    }

    /**
     * 获取某一机构的所有课程评论统计
     *
     * @return {@link R}<{@link DiscussStatisticsVo}>
     */
    @GetMapping("/listAll")
    @SaCheckRole(
        value = {
            "organization",
            "operator"
        },
        mode = SaMode.OR
    )
    public R<List<DiscussStatisticsVo>> getInfoByCompanyId() {
        Long companyId = LoginHelper.getDeptId();
        return R.ok(discussStatisticsService.queryByCompanyId(companyId));
    }

    /**
     * 新增评论统计，机构用
     */
    @Log(title = "评论统计，机构用", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody DiscussStatisticsBo bo) {
        return toAjax(discussStatisticsService.insertByBo(bo));
    }

    /**
     * 修改评论统计，机构用
     */
    @Log(title = "评论统计，机构用", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody DiscussStatisticsBo bo) {
        return toAjax(discussStatisticsService.updateByBo(bo));
    }

    /**
     * 删除评论统计，机构用
     *
     * @param ids 主键串
     */
    @Log(title = "评论统计，机构用", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(discussStatisticsService.deleteWithValidByIds(List.of(ids), true));
    }
}
