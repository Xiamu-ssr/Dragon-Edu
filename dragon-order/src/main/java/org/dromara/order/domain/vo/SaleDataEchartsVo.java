package org.dromara.order.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 销售统计数据
 * <br/>
 * 范围可自行选择
 *
 * @author mumu
 * @date 2024/05/06
 */
@Data
public class SaleDataEchartsVo {

    /**
     * X轴
     */
    private List<String> xAxis;

    /**
     * 销售量
     */
    private List<Integer> saleNum;

    /**
     * 销售额
     */
    private List<BigDecimal> saleMoney;

    /*
    * 销售平均值，销售增长率，前端自己算。
    * */
}
