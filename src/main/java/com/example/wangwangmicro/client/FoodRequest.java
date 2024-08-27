package com.example.wangwangmicro.client;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FoodRequest {
    private int userId;
    private int foodId;
    private int tripId;
    private BigDecimal payment;
    private int quantity;
}
