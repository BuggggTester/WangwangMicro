package com.example.wangwangmicro.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hotel-service")  // "hotel-service" 是你在 Kubernetes 中为 hotel 服务定义的服务名称
public interface HotelServiceClient {

    @GetMapping("/hotel/checkAvailability")  // 对应 hotel 服务中的接口路径
    boolean checkRoomAvailability(@RequestParam("hotelId") int hotelId,
                                  @RequestParam("roomType") String roomType,
                                  @RequestParam("startDate") String startDate,
                                  @RequestParam("endDate") String endDate);
}
