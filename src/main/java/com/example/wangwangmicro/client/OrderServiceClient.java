package com.example.wangwangmicro.client;

import com.example.wangwangmicro.Entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @PostMapping("/orders")
    String createOrder(@RequestBody Order order);

}
