package org.dromara.media.enums;

public enum MediaStatusEnum {
    UNREVIEWED(1, "未审核"),
    REVIEW_PASSED(3, "审核通过"),
    REVIEW_FAILED(4, "审核未通过"),
    UNDER_REVIEW(2, "审核中")
    ;

    private final int value;
    private final String description;

    MediaStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static MediaStatusEnum fromValue(int value) {
        for (MediaStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unknown value for MediaStatusEnum: " + value);
    }
}
