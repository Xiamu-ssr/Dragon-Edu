package org.dromara.order.domain.bo;

import org.dromara.order.domain.SaleData;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;
/**
 * 销售总量统计业务对象 sale_data
 *
 * @author LionLi
 * @date 2024-05-06
 */
@Data
@AutoMapper(target = SaleData.class, reverseConvertGenerate = false)
public class SaleDataBo implements Serializable {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 公司id
     */
    @NotNull(message = "公司id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long companyId;

    /**
     * 销售总量
     */
    @NotNull(message = "销售总量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long saleNum;

    /**
     * 销售总额
     */
    @NotNull(message = "销售总额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal revenue;


}
