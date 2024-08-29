package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.entity.food.Food;
import com.example.wangwangmicro.client.TripClient;
import com.example.wangwangmicro.client.TripRequest;
import com.example.wangwangmicro.entity.R;
import com.example.wangwangmicro.entity.food.FoodReservation;
import com.example.wangwangmicro.service.FoodService;
//import com.example.demo.service.TripService;
//import org.springframework.web.client.RestTemplate; //用于http调用引入其他容器的信息功能
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.wangwangmicro.client.OrderClient;
import com.example.wangwangmicro.client.OrderRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static com.example.wangwangmicro.config.PathConfig.food;
import static com.example.wangwangmicro.config.PathConfig.foodUrl;

@CrossOrigin
@Slf4j
@Controller
@RestController
@RequestMapping(value ="/food")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private TripClient tripClient;
    @Autowired
    private OrderClient orderClient;

    @RequestMapping(value="/select/tripId")
    public ResponseEntity<List<Food>> selectFoodsById(@RequestParam("trainId") String trainId, @RequestParam("time") Timestamp time) {
        TripRequest tripRequest = new TripRequest();
        tripRequest.setTrainId(trainId);
        tripRequest.setTime(time);
        
        Integer tripId = tripClient.selectIdByTrainAndTime(tripRequest);
        
        if (tripId == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Food> foodList = foodService.selectFoodsByTripId(tripId);        
        return ResponseEntity.ok(foodList);
    }

    @RequestMapping(value = "/create")
    public R createFood(@RequestParam("foodName") String foodName, @RequestParam("price")double price,@RequestParam("tripId")int tripId) {
        try {
            foodService.createFood(foodName, price, tripId, "/file/foods/default.png");
        }catch (Exception e) {
            return R.error(e.toString());
        }
        return R.ok("创建成功！");
    }

    @RequestMapping(value="/create/reservation")
    public R createFoodReservation(@RequestBody FoodReservation foodReservation) {
        try {
            foodService.buyFood(foodReservation);
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setFoodId(foodReservation.getFoodId());
            orderRequest.setUserId(foodReservation.getUserId());
            orderRequest.setTripId(foodReservation.getTripId());
            orderRequest.setQuantity(foodReservation.getQuantity());
            orderClient.createOrder(orderRequest);
            return R.ok("购买成功").put("reservationId", foodReservation.getId());
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
    
    @PostMapping(value="/upload/image")
    public R uploadFile(@RequestParam("File") MultipartFile file, @RequestParam("foodId") int foodId) {
        String filePath = foodUrl;
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String fileNewName = UUID.randomUUID() + fileType;
        File targetFile = new File(filePath);
        foodService.uploadFoodImage( food+ fileNewName, foodId);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileNewName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            return R.error(e.toString());
        }
        return R.ok("上传成功！");
    }
}
