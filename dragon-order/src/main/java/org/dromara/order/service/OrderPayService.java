package org.dromara.order.service;

import org.dromara.common.core.domain.R;
import org.dromara.order.domain.PayStatusDto;
import org.dromara.order.domain.vo.PayQRCodeVo;

public interface OrderPayService {

    /**
     * 生成付款代码
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return {@link PayQRCodeVo}
     */
    public PayQRCodeVo generatePayCode(Long userId, Long courseId);

    /**
     * (主动)查询支付结果
     *
     * @param payNo 不付款
     * @return {@link R}<{@link Void}>
     */
    boolean queryPayResult(Long payNo);

    /**
     * 保存支付状态
     *
     * @param payStatusDto 支付状态dto
     */
    boolean saveAliPayStatus(PayStatusDto payStatusDto);
}
