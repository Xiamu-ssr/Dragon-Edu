package org.dromara.order.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;

import java.math.BigDecimal;

@Data
public class OrderPayBo {
    @NotNull(message = "课程id不能为空", groups = { AddGroup.class})
    private Long courseId;
    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class})
    private String courseName;
    @NotNull(message = "机构id不能为空", groups = { AddGroup.class})
    private Long companyId;
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class})
    private Long userId;
    @NotNull(message = "金额不能为空", groups = { AddGroup.class})
    private BigDecimal price;
}
