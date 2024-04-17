package org.dromara.course.enums;

public enum CourseStatusEnum {
    UNPUBLISHED(1, "未发布"),
    UNDER_REVIEW(2, "审核中"),
    REVIEW_FAILED(3, "审核不通过"),
    REVIEW_PASSED(4, "审核通过")
    ;

    private final int value;
    private final String description;

    CourseStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static CourseStatusEnum fromValue(int value) {
        for (CourseStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unknown value for CourseStatusEnum: " + value);
    }
}
