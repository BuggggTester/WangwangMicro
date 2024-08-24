package com.example.wangwangmicro.Entity;

import com.example.wangwangmicro.constant.SeatType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@Component
@Slf4j
@Entity
public class TripOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "trip_id")
    private int tripId;

    @Column(nullable = false, name = "carriage")
    private int carriage;

    @Column(nullable = false, name = "`row`")
    private int row;

    @Column(nullable = false, name = "seat")
    private char seat;

    @Column(nullable = false, name = "departure_id")
    private int departureId;

    @Column(nullable = false, name = "destination_id")
    private int destinationId;

    @Column(nullable = false, name = "seat_type")
    private SeatType seatType;

    @Column(nullable = false, name = "passage_id")
    private int passageId;
}
