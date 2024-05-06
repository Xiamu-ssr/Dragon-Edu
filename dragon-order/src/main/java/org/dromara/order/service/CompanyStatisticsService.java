package org.dromara.order.service;

import org.dromara.common.core.domain.R;
import org.dromara.order.domain.vo.TotalStatisticsVo;

public interface CompanyStatisticsService {
    public TotalStatisticsVo getTotalData(Long companyId);

    public TotalStatisticsVo getTotalDataCache(Long companyId);
}
