package com.example.wangwangmicro.entity.hotel;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.example.wangwangmicro.entity.myenum.RoomType;

@Data
@Slf4j
@Component
public class Room {
    @Id
    private int room_id;
    private String name;
    @Enumerated(EnumType.STRING)
    private RoomType room_type;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private int hotel_id;
    private Double price;
    private String description;
    private String picture_path;
    private int available_quantity;

}
