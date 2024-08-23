package com.example.wangwanghotelpart.service;

import com.example.wangwanghotelpart.common.constant.RoomType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface HotelService {

    boolean hasAbilityRoom(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate);

    int getAbilityRoomQuantity(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate);

    int bookRoom(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate);
}
