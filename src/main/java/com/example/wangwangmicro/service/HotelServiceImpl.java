package com.example.wangwangmicro.service;

import com.example.wangwangmicro.client.OrderClient;
import com.example.wangwangmicro.common.constant.RoomType;
import com.example.wangwangmicro.dao.HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;


    public boolean isRoomAvailable(int hotelId, String roomType, String startDate, String endDate) {
        return true;
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
    public boolean bookRoom(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate) {
        return hotelMapper.bookRoom(hotelId, roomType, startDate, endDate);
    }

    @Override
    public int cancelRoom(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate) {
        return hotelMapper.cancelRoom(hotelId, roomType, startDate, endDate);
    }
}
