package com.example.wangwangmicro.common.constant;

/**
 * Enum representing the status of an order.
 */
public enum OrderStatus {
    PENDING_PAYMENT("Pending Payment"),
    PAID("Paid"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
