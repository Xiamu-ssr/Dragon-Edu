package org.dromara.order.domain.vo;

import java.math.BigDecimal;
import org.dromara.order.domain.Order;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 订单服务视图对象 order
 *
 * @author LionLi
 * @date 2024-04-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Order.class)
public class OrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 课程id
     */
    @ExcelProperty(value = "课程id")
    private Long courseId;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String courseName;

    /**
     * 机构id
     */
    private Long companyId;

    /**
     * 用户id
     */
    private Long userId;

    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 金额
     */
    @ExcelProperty(value = "金额")
    private BigDecimal price;

    /**
     * 选课状态：1选课成功、2待支付、3选课删除。
     */
    @ExcelProperty(value = "订单状态：1未支付、2支付成功、3支付失败。")
    private Integer status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remarks;


}
