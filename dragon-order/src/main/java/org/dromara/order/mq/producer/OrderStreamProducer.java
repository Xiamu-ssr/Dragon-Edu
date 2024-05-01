package org.dromara.order.mq.producer;

import org.dromara.common.mq.domain.OrderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

/**
 * 订单流生产者
 *
 * @author mumu
 * @date 2024/04/30
 */
@Component
public class OrderStreamProducer {
    @Autowired
    private StreamBridge streamBridge;

    public void orderSupplier(OrderMessage order) {
        streamBridge.send("orderSupplier-out-0", order);
    }
}
