package org.dromara.learn.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.mq.domain.OrderMessage;
import org.dromara.common.mq.domain.TestMessaging;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderStreamConsumer {

    /**
     * 购买课程成功
     * <br></>
     * 后续操作-更新课程表
     *
     * @return {@link Consumer}<{@link TestMessaging}>
     */
    @Bean
    Consumer<OrderMessage> buyCourseSuccess() {
        log.info("初始化订阅");
        return msg -> {
            log.info("通过stream消费到消息 => {}", msg.toString());
            throw new AmqpRejectAndDontRequeueException("failed");
        };
    }
}
