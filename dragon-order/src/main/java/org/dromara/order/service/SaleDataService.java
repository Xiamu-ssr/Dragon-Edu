package org.dromara.order.service;

import org.dromara.order.domain.SaleData;
import org.dromara.order.domain.vo.SaleDataVo;
import org.dromara.order.domain.bo.SaleDataBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 销售总量统计Service接口
 *
 * @author LionLi
 * @date 2024-05-06
 */
public interface SaleDataService {

    /**
     * 查询销售总量统计
     */
    SaleDataVo queryById(Long id);

    /**
     * 查询销售总量统计列表
     */
    TableDataInfo<SaleDataVo> queryPageList(SaleDataBo bo, PageQuery pageQuery);

    /**
     * 查询销售总量统计列表
     */
    List<SaleDataVo> queryList(SaleDataBo bo);

    /**
     * 新增销售总量统计
     */
    Boolean insertByBo(SaleDataBo bo);

    /**
     * 修改销售总量统计
     */
    Boolean updateByBo(SaleDataBo bo);

    /**
     * 校验并批量删除销售总量统计信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 推送销售数据
     * <br/>
     * 定时任务用，如果company_id数据不存在则会新建
     *
     * @param companyId 公司id
     * @param saleNum   销售数量
     * @param revenue   收入
     * @return {@link Boolean}
     */
    Boolean pushSaleData(Long companyId, Long saleNum, BigDecimal revenue);
}
