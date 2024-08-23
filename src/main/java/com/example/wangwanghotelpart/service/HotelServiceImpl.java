package com.example.wangwanghotelpart.service;

import com.example.wangwanghotelpart.common.constant.RoomType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HotelServiceImpl implements HotelService {
    @Override
    public boolean hasAbilityRoom(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate) {
        return false;
    }

    @Override
    public int getAbilityRoomQuantity(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate) {
        return 0;
    }

    @Override
    public int bookRoom(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate) {
        return 0;
    }
}
