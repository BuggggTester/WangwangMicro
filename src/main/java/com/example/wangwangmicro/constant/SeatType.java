package com.example.wangwangmicro.constant;

/**
 *
 */
public enum SeatType {
    FIRST_CLASS("一等座"),
    SECOND_CLASS("二等座"),
    BUSINESS_CLASS("商务座");

    private final String description;

    SeatType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
