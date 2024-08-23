package com.example.wangwangmicro.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    @Column(name = "check_in_date", nullable = false)
    private Date checkInDate;

    @Column(name="check_out_date", nullable = false)
    private Date checkOutDate;
}

