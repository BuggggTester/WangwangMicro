package com.example.wangwangmicro.service.impl;

import com.example.wangwangmicro.dao.FoodMapper;
import com.example.wangwangmicro.entity.R;
import com.example.wangwangmicro.entity.food.Food;
import com.example.wangwangmicro.entity.food.FoodReservation;
import com.example.wangwangmicro.service.FoodService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("foodService")
public class FoodServiceImpl implements FoodService {
    @Resource
    private FoodMapper foodMapper;
    @Override
    public List<Food> selectFoodsByTripId(int tripId) {
        return foodMapper.selectFoodsByTripId(tripId);
    }

    @Override
    public void uploadFoodImage(String image, int foodId) {
        foodMapper.uploadFoodImage(image, foodId);
    }

    @Override
    public FoodReservation selectFoodReservationById(int reservation_id) {
        return foodMapper.selectFoodReservationById(reservation_id);
    }


    @Override
    public R buyFood(FoodReservation foodReservation) {
        int stock = foodMapper.checkStock(foodReservation.getFoodId());
        if (stock < foodReservation.getQuantity()) {
            return R.error("库存不足");
        }
        foodMapper.buyFood(foodReservation);
        foodMapper.reduceStock(foodReservation.getFoodId(), foodReservation.getQuantity());
        return R.ok("购买成功").put("reservationId", foodReservation.getId());
    }

    @Override
    public void createFood(String foodName, double price, int tripId, String image) {
        foodMapper.createFood(foodName, price, tripId, image);
    }

    @Override
    public List<Food> selectFoodByTripId(int trip_id) {
        return foodMapper.selectFoodByTripId(trip_id);
    }

//    @Override
//    public void cancelFood(int reservationID) {
//        foodMapper.cancelFood(reservationID);
//    }
}
