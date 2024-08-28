package com.example.wangwangmicro.client;

import com.example.wangwangmicro.constant.SeatType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    private int tripId;
    private int carriage;
    private int row;
    private char seat;
    private int departureId;
    private int destinationId;
    private SeatType seatType;
    private int passageId;
    private BigDecimal payment;
}
