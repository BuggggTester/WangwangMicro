package com.example.wangwangmicro.Entity;

import com.example.wangwangmicro.constant.RoomType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Slf4j
@Component
public class HotelOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "hotel_id")
    private int hotelId;

    @Column(nullable = false, name = "room_type")
    private RoomType roomType;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name="check_out_date", nullable = false)
    private LocalDate checkOutDate;
}

