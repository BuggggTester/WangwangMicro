package com.example.wangwangmicro.entity.order;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.example.wangwangmicro.entity.Food;

import java.sql.Time;

@Data
@Slf4j
@Component
public class FoodOrder {
    int food_order_id;
    @Transient
    private int food_id;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
    private Time order_time;
    private int state;

}
