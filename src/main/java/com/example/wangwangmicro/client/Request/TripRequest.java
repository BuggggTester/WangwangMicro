package com.example.wangwangmicro.client.Request;


import com.example.wangwangmicro.constant.SeatType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TripRequest {
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
