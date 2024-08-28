package com.example.wangwangmicro.client.Requeat;

import com.example.wangwangmicro.constant.RoomType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class HotelRequest {
    private int userId;
    private BigDecimal payment;


    private int hotelId;
    private RoomType roomType;
    private LocalDate startDate;
    private LocalDate endDate;
}
