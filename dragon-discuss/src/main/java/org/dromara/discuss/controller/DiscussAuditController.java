package org.dromara.discuss.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.web.core.BaseController;
import org.dromara.discuss.domain.Discuss;
import org.dromara.discuss.domain.bo.DiscussBo;
import org.dromara.discuss.domain.vo.DiscussVo;
import org.dromara.discuss.enums.DiscussStatusEnum;
import org.dromara.discuss.mapper.DiscussMapper;
import org.dromara.discuss.service.DiscussAuditService;
import org.dromara.discuss.service.DiscussService;
import org.dromara.learn.api.RemoteScheduleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程评论
 * 前端访问路由地址为:/discuss/discuss
 *
 * @author LionLi
 * @date 2024-05-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/audit")
public class DiscussAuditController extends BaseController {

    private final DiscussAuditService auditService;

    private final DiscussMapper discussMapper;


    /**
     * 查询课程评论列表
     */
    @GetMapping("/list")
    public TableDataInfo<DiscussVo> list(DiscussBo bo, PageQuery pageQuery) {
        return auditService.queryPageList(bo, pageQuery);
    }

    /**
     * 是否允许屏蔽？
     */
    @GetMapping("/allowForBlock/{id}")
    public R<String> allowForBlock(
        @NotNull(message = "主键不能为空") @PathVariable Long id,
        @RequestParam boolean allow
    ) {
        long status = DiscussStatusEnum.APPLYBLOCK.getValue();
        if (allow){
            status = DiscussStatusEnum.BLOCK.getValue();
        }else {
            status = DiscussStatusEnum.SUCCESS.getValue();
        }
        boolean b = discussMapper.update(new LambdaUpdateWrapper<Discuss>()
            .eq(Discuss::getId, id)
            .set(Discuss::getStatus, status)
        ) > 0;
        if (b){
            return R.ok("审核成功");
        }else {
            return R.fail("审核失败");
        }
    }
}
