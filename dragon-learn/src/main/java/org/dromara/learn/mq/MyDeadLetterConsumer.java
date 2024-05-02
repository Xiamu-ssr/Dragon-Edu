package org.dromara.learn.mq;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.mq.domain.OrderMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyDeadLetterConsumer {

    @RabbitListener(queues = "orderExchange.learnGroup.dlq")
    public void listenErrorQueue(String msg){
        log.error("订单支付成功,但是无法完成后续操作,请及时处理,消息：[" + msg + "]");
    }

}
