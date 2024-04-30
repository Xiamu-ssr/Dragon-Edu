package org.dromara.order.domain.vo;

import lombok.Data;

@Data
public class PayQRCodeVo {

    /**
     * qrcode
     */
    private String qrcode;

    /**
     * 订单号
     */
    private Long payNo;

}
