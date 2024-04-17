package com.yourpackage.enums;

public enum ${enumTypeName} {
<#list items as item>
    ${item.eng}(${item.value}, "${item.label}")<#if item_has_next>,</#if>
</#list>;

    private final int value;
    private final String description;

    ${enumTypeName}(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ${enumTypeName} fromValue(int value) {
        for (${enumTypeName} item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unknown value for ${enumTypeName}: " + value);
    }
}
