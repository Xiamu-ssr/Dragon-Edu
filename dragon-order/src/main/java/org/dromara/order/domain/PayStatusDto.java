package org.dromara.order.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PayStatusDto {

    //订单号
    Long out_trade_no;
    //支付宝交易号
    String trade_no;
    //交易状态
    String trade_status;
    //appid
    String app_id;
    //total_amount
    String total_amount;
}
