package org.dromara.order.enums;

public enum OrderStatusEnum {
    UNPAID(1, "未支付"),
    PAID_SUCCESS(2, "支付成功"),
    PAID_FAILED(3, "支付失败")
    ;

    private final int value;
    private final String description;

    OrderStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatusEnum fromValue(int value) {
        for (OrderStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unknown value for OrderStatusEnum: " + value);
    }
}
