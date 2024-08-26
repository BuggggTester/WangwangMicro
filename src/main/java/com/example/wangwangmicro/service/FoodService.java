package com.example.wangwangmicro.service;

import com.example.wangwangmicro.entity.R;
import com.example.wangwangmicro.entity.food.Food;
import com.example.wangwangmicro.entity.food.FoodReservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FoodService {
    List<Food> selectFoodsByTripId(int tripId);
    void uploadFoodImage(String image, int foodId);

    FoodReservation selectFoodReservationById(int reservation_id);

    R buyFood(FoodReservation foodReservation);
    void createFood(String foodName, double price, int tripId, String image);
    List<Food> selectFoodByTripId(int trip_id);

//    void cancelFood(int restaurantId);
}
