package org.dromara.common.messagesdk.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum MessageTypeEnum {
    // 为每个枚举值定义附加字段
    COURSE_PUBLISH("course_publish");

    private final String value;  // 描述信息

    // 枚举的构造函数
    MessageTypeEnum(String value) {
        this.value = value;
    }
}
