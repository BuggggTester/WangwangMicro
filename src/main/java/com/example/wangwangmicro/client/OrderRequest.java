package com.example.wangwangmicro.client;
import lombok.Data;
import java.math.BigDecimal;

import com.example.wangwangmicro.common.constant.SeatType;

@Data
public class OrderRequest {
    private int userId;
    private BigDecimal payment;
    private int passageId;


    private int tripId;
    private int carriage;
    private int row;
    private char seat;
    private int departureId;
    private int destinationId;
    private SeatType seatType;
}
