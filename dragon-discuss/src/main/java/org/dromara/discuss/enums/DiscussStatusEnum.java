package org.dromara.discuss.enums;

public enum DiscussStatusEnum {
    SUCCESS(0, "正常"),
    APPLYBLOCK(1, "申请屏蔽"),
    BLOCK(2, "屏蔽")
    ;

    private final int value;
    private final String description;

    DiscussStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static DiscussStatusEnum fromValue(int value) {
        for (DiscussStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unknown value for DiscussStatusEnum: " + value);
    }
}
