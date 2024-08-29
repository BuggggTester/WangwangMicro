package com.example.wangwangmicro.client;

import com.example.wangwangmicro.client.Request.TripRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "trip-service")  // "hotel-service" 是你在 Kubernetes 中为 hotel 服务定义的服务名称
public interface TripClient {

    @GetMapping("/trip/cancelOrder")
        // 对应 hotel 服务中的接口路径
    int cancelOrder(@RequestBody TripRequest tripRequest);


}
