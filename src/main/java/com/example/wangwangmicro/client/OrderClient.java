package com.example.wangwangmicro.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderClient {
    @GetMapping("/trip/buyTrip")
        // 对应 hotel 服务中的接口路径
    int createOrder(@RequestBody OrderRequest orderRequest);
}
