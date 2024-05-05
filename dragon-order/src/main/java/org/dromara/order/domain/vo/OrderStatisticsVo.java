package org.dromara.order.domain.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.order.domain.OrderStatistics;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 订单统计视图对象 order_statistics
 *
 * @author LionLi
 * @date 2024-05-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OrderStatistics.class)
public class OrderStatisticsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 机构id
     */
    @ExcelProperty(value = "机构id")
    private Long companyId;

    /**
     * 销售量
     */
    @ExcelProperty(value = "销售量")
    private Long saleNum;

    /**
     * 销售额
     */
    @ExcelProperty(value = "销售额")
    private BigDecimal sale;

    /**
     * 日期
     */
    @ExcelProperty(value = "日期")
    private LocalDate statDate;


}
