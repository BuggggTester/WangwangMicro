package com.example.wangwangmicro.client.Requeat;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FoodRequest {
    private int userId;
    private BigDecimal payment;


    private int foodId;
    private int tripId;
    private int quantity;
}
