package org.dromara.order.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<Integer> getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(List<Integer> saleNum) {
        this.saleNum = saleNum;
    }

    public List<BigDecimal> getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(List<BigDecimal> saleMoney) {
        this.saleMoney = saleMoney;
    }

    /*
    * 销售平均值，销售增长率，前端自己算。
    * */

}
