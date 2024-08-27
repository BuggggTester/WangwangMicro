package com.example.wangwangmicro.client;

import com.example.wangwangmicro.constant.RoomType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.sql.DataSourceDefinitions;

@Data
public class FoodRequest {
    private int userId;
    private int foodId;
    private BigDecimal payment;
}
