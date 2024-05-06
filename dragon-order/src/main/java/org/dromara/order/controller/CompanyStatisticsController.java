package org.dromara.order.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import org.dromara.common.core.domain.R;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.order.domain.vo.TotalStatisticsVo;
import org.dromara.order.service.CompanyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构端总分析
 * <br/>
 * 依附于order模块之上
 *
 * @author mumu
 * @date 2024/05/06
 */
@RestController
@RequestMapping("/totalStatistics")
public class CompanyStatisticsController {

    @Autowired
    CompanyStatisticsService companyStatisticsService;

    @GetMapping("/data")
    @SaCheckRole("organization")
    public R<TotalStatisticsVo> getTotalData(){
        Long companyId = LoginHelper.getDeptId();
        TotalStatisticsVo dataCache = companyStatisticsService.getTotalDataCache(companyId);
        return R.ok(dataCache);
    }

}
