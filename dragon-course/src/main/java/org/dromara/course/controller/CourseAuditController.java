package org.dromara.course.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.course.domain.bo.CourseBaseBo;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.service.CourseAuditService;
import org.dromara.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit")
public class CourseAuditController {

    @Autowired
    private CourseAuditService courseAuditService;

    @GetMapping("/list")
    public TableDataInfo<CourseBaseVo> list(CourseBaseBo bo, PageQuery pageQuery) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        bo.setCompanyId(loginUser.getDeptId());
        return courseAuditService.queryPageList(bo, pageQuery);
    }

    @GetMapping("/pass")
    public R<Boolean> pass(Long id) {
        Boolean b = courseAuditService.pass(id);
        if (b){
            return R.ok("审核成功");
        }else {
            return R.fail("审核失败");
        }
    }

    @PutMapping("/reject")
    public R<Boolean> reject(@RequestBody JsonNode bo) {
        Boolean b = courseAuditService.reject(bo);
        if (b){
            return R.ok("审核成功");
        }else {
            return R.fail("审核失败");
        }
    }
}
