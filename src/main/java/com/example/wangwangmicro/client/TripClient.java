package com.example.wangwangmicro.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hotel-service")
public interface TripClient {
    @PostMapping("/select/tripId") //接口路径暂时
    int selectIdByTrainAndTime(@RequestBody TripRequest hotelRequest);
}
