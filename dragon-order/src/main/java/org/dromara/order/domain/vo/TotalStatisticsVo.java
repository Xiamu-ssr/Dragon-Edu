package org.dromara.order.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TotalStatisticsVo {
    /**
     * 订单总数量
     */
    private Long orderNum;

    /**
     * 本周订单数
     */
    private Long orderThisWeek;

    /**
     * 总收入
     */
    private BigDecimal revenue;

    /**
     * 上周盈利增长率
     */
    private Integer revenueGrowRate;

    /**
     * 课程数量
     */
    private  Integer courseNum;
    /**
     * 章节数量
     */
    private  Integer teachplanNum;

    /**
     * 评论总数
     */
    private Integer discussNum;

    /**
     * 每门课平均评论数
     */
    private Integer discussAvg;

    /**
     * 最近订单-10个
     */
    private List<CurrentOrdersVo> currentOrders;

    /**
     * 销售数据-默认14天
     */
    private SaleDataEchartsVo saleData;

    /**
     * 最佳课程-5个
     */
    private List<BestCourseVo> bestCourse;


}
