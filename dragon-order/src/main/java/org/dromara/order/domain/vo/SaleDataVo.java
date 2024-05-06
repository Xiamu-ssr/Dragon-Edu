package org.dromara.order.domain.vo;

import java.math.BigDecimal;
import org.dromara.order.domain.SaleData;
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
 * 销售总量统计视图对象 sale_data
 *
 * @author LionLi
 * @date 2024-05-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SaleData.class)
public class SaleDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 公司id
     */
    @ExcelProperty(value = "公司id")
    private Long companyId;

    /**
     * 销售总量
     */
    @ExcelProperty(value = "销售总量")
    private Long saleNum;

    /**
     * 销售总额
     */
    @ExcelProperty(value = "销售总额")
    private BigDecimal revenue;


}
