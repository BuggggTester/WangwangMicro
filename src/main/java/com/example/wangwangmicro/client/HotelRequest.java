package com.example.wangwangmicro.client;

import com.example.wangwangmicro.constant.RoomType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class HotelRequest {
    private int userId;
    private int hotelId;
    private RoomType roomType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal payment;
}
