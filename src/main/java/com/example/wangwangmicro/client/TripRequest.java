package com.example.wangwangmicro.client;


import com.example.wangwangmicro.constant.SeatType;
import lombok.Data;

@Data
public class TripRequest {
    private int tripId;
    private int carriage;
    private int row;
    private char seat;
    private int departureId;
    private int destinationId;
    private SeatType seatType;
    private int passageId;
}
