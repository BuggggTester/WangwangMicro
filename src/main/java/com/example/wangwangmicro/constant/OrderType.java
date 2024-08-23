package com.example.wangwangmicro.constant;

public enum OrderType {
    TRAIN_TICKET("Train Ticket"),
    TRAIN_MEAL("Train Meal"),
    HOTEL("Hotel");

    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
