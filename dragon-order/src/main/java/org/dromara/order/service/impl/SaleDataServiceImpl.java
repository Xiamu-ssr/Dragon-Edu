package org.dromara.order.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.order.domain.bo.SaleDataBo;
import org.dromara.order.domain.vo.SaleDataVo;
import org.dromara.order.domain.SaleData;
import org.dromara.order.mapper.SaleDataMapper;
import org.dromara.order.service.SaleDataService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collection;

/**
 * 销售总量统计Service业务层处理
 *
 * @author LionLi
 * @date 2024-05-06
 */
@RequiredArgsConstructor
@Service
public class SaleDataServiceImpl implements SaleDataService {

    private final SaleDataMapper saleDataMapper;

    /**
     * 查询销售总量统计
     */
    @Override
    public SaleDataVo queryById(Long id){
        return saleDataMapper.selectVoById(id);
    }

    /**
     * 查询销售总量统计列表
     */
    @Override
    public TableDataInfo<SaleDataVo> queryPageList(SaleDataBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SaleData> lqw = buildQueryWrapper(bo);
        Page<SaleDataVo> result = saleDataMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询销售总量统计列表
     */
    @Override
    public List<SaleDataVo> queryList(SaleDataBo bo) {
        LambdaQueryWrapper<SaleData> lqw = buildQueryWrapper(bo);
        return saleDataMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SaleData> buildQueryWrapper(SaleDataBo bo) {
        LambdaQueryWrapper<SaleData> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCompanyId() != null, SaleData::getCompanyId, bo.getCompanyId());
        lqw.eq(bo.getSaleNum() != null, SaleData::getSaleNum, bo.getSaleNum());
        lqw.eq(bo.getRevenue() != null, SaleData::getRevenue, bo.getRevenue());
        return lqw;
    }

    /**
     * 新增销售总量统计
     */
    @Override
    public Boolean insertByBo(SaleDataBo bo) {
        SaleData add = MapstructUtils.convert(bo, SaleData.class);
        validEntityBeforeSave(add);
        boolean flag = saleDataMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改销售总量统计
     */
    @Override
    public Boolean updateByBo(SaleDataBo bo) {
        SaleData update = MapstructUtils.convert(bo, SaleData.class);
        validEntityBeforeSave(update);
        return saleDataMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SaleData entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除销售总量统计
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return saleDataMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean pushSaleData(Long companyId, Long saleNum, BigDecimal revenue) {
        SaleData saleData = saleDataMapper.selectOne(new LambdaQueryWrapper<SaleData>()
            .eq(SaleData::getCompanyId, companyId));
        if (saleData != null){
            saleData.setSaleNum(saleData.getSaleNum() + saleNum);
            saleData.setRevenue(saleData.getRevenue().add(revenue));
        }else {
            saleData = new SaleData();
            saleData.setCompanyId(companyId);
            saleData.setSaleNum(saleNum);
            saleData.setRevenue(revenue);
        }
        return saleDataMapper.insertOrUpdate(saleData);
    }
}
