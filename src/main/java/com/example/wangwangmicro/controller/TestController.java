package com.example.wangwangmicro.controller;

import org.springframework.web.bind.annotation.*;

import com.example.wangwangmicro.entity.common.R;
import com.example.wangwangmicro.entity.order.TripOrder;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    @PostMapping(value="/test1")
    public R test1(@RequestBody TripOrder tripOrder){
        try{
            return R.ok().put("111",tripOrder.getTrip_order_id());

        }catch (Exception e){
            return R.error(e.toString());
        }
    }

}
