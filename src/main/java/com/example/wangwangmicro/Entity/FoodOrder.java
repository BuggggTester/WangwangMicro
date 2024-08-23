package com.example.wangwangmicro.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity
@Data
@Slf4j
@Component
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "food_id")
    private int foodId;

    @Column(nullable = false, name = "trip_id")
    private int tripId;

    @Column(nullable = false, name = "quantity")
    private int quantity = 0;

}
