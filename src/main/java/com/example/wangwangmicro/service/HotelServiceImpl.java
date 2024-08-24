package com.example.wangwangmicro.service;

import com.example.wangwangmicro.client.HotelServiceClient;
import com.example.wangwangmicro.common.constant.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelServiceClient hotelServiceClient;

    public boolean isRoomAvailable(int hotelId, String roomType, String startDate, String endDate) {
        return hotelServiceClient.checkRoomAvailability(hotelId, roomType, startDate, endDate);
    }

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
