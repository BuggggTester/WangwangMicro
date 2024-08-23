package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.entity.food.Food;
import com.example.wangwangmicro.entity.R;
import com.example.wangwangmicro.entity.food.FoodReservation;
import com.example.wangwangmicro.service.FoodService;
//import com.example.demo.service.TripService;
import org.springframework.web.client.RestTemplate; //用于http调用引入其他容器的信息功能
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    //private TripService tripService;
    private RestTemplate restTemplate;
    @RequestMapping(value="/select/tripId")
    public List<Food> selectFoodsById(@RequestParam("trainId")String trainId, @RequestParam("time") Timestamp time) {
        //int tripId = tripService.selectIdByTrainAndTime(trainId, time);
        // 使用 RestTemplate 调用 TripService API：still unkown hahaha
        String url = "http://trip-service/api/trips/findId?trainId=" + trainId + "&time=" + time;
        Integer tripId = restTemplate.getForObject(url, Integer.class); 
        if (tripId == null) {
            return List.of(); // 错误响应报一下
        }
        List<Food> foodList = foodService.selectFoodsByTripId(tripId);
        return foodList;
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
            return R.ok("create reservation success").put("reservationId",foodReservation.getId());
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
