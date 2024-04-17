package org.dromara.course.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.dromara.common.messagesdk")
public class MessageSdkConfig {
    // 其他需要的bean定义可以放在这里
}
