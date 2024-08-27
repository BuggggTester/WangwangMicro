package com.example.wangwangmicro.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.example.wangwangmicro.entity.myenum.OrderType;
import com.example.wangwangmicro.entity.order.FoodOrder;
import com.example.wangwangmicro.entity.order.HotelOrder;
import com.example.wangwangmicro.entity.order.TripOrder;

import java.sql.Time;

@Data
@Slf4j
@Component
public class Order {
    @Id
    private String order_id;
    @Enumerated(EnumType.STRING)
    private OrderType order_type;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private Time create_time;
    private int state;
    private FoodOrder foodOrder;
    private HotelOrder hotelOrder;
    private TripOrder tripOrder;
}
