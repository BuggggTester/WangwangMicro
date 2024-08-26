package com.example.wangwangmicro.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service") //
public interface OrderClient{ //调用order微服务的创建订单接口
    @PostMapping("/food/buyFood")
    int createOrder(@RequestBody OrderRequest orderRequest);

    //@PostMapping("/orders/cancel")
    //void cancelOrder(@RequestParam("orderId") String orderId);
}