package com.example.wangwangmicro.dao;

import com.example.wangwangmicro.entity.food.Food;
import com.example.wangwangmicro.entity.food.FoodReservation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoodMapper {
    @Select("select * from food where trip_id = #{tripId}")
    List<Food> selectFoodsByTripId(int tripId);
    @Update("update food set picture_path = #{image} where id = #{foodId}")
    void uploadFoodImage(String image, int foodId);
    @Insert("insert into food (name, picture_path, price, trip_id) VALUES (#{foodName},#{image}, #{price},#{tripId})")
    void createFood(String foodName, double price, int tripId, String image);
    @Insert("INSERT INTO food_reservation (food_id, user_id, quantity) " +
            "VALUES (#{food_id}, #{user_id}, #{quantity})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID() AS id", keyProperty = "id", before = false, resultType = int.class)
    void buyFood(FoodReservation foodReservation);
    @Select("select * from food_reservation where id = #{reservation_id} limit 1")
    @Results({
            @Result(property = "food", column = "food_id",
                    one = @One(select = "com.example.demo.dao.FoodMapper.selectFoodById")),
            @Result(property = "user", column = "user_id", one = @One(select = "com.example.demo.dao.UserMapper.selectUserById"))
    })
    FoodReservation selectFoodReservationById(int reservation_id);
    @Select("select * from food where trip_id = #{trip_id}")
    List<Food> selectFoodByTripId(int trip_id);
    @Select("select * from food where id = #{food_id} limit 1")
    @Results({
            @Result(property = "trip", column = "trip_id", one = @One(select = "com.example.demo.dao.TripMapper.selectTripById"))
    })
    Food selectFoodById(int food_id);
}
