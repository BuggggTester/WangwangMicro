package com.example.wangwangmicro.client;

import com.example.wangwangmicro.common.constant.RoomType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderRequest {
    private int hotelId;
    private RoomType roomType;
    private LocalDate startDate;
    private LocalDate endDate;
}

