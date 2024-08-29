package com.example.wangwangmicro.dao;

import com.example.wangwangmicro.common.constant.RoomType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;

@Mapper
public interface HotelMapper {
    @Select("SELECT MIN(quantity) FROM roomability " +
            "WHERE hotel_id = #{hotelId} AND room_type = #{roomType} " +
            "AND date BETWEEN #{startDate} AND #{endDate}")
    int getAbilityRoomQuantity(@Param("hotelId") int hotelId,
                               @Param("roomType") RoomType roomType,
                               @Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate);

    @Update("UPDATE roomability SET quantity = quantity - 1 " +
            "WHERE hotel_id = #{hotelId} AND room_type = #{roomType} AND date BETWEEN #{startDate} AND #{endDate} " +
            "AND quantity > 0")
    int bookRoom(@Param("hotelId") int hotelId,
                 @Param("roomType") RoomType roomType,
                 @Param("startDate") LocalDate startDate,
                 @Param("endDate") LocalDate endDate);


    @Update("UPDATE roomability SET quantity = quantity + 1 " +
            "WHERE hotel_id = #{hotelId} AND room_type = #{roomType} AND date BETWEEN #{startDate} AND #{endDate}")
    int cancelRoom(int hotelId, RoomType roomType, LocalDate startDate, LocalDate endDate);
}
