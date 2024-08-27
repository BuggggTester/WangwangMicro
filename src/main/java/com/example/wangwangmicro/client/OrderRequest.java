package com.example.wangwangmicro.client;
import lombok.Data;

@Data
public class OrderRequest {
    private int userId;
    private int foodId;
    private int tripId;
    private int quantity;
}
