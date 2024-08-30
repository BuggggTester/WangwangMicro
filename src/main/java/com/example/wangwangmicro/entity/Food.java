package com.example.wangwangmicro.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@Entity
public class Food {
    @Id
    private int food_id;
    private String name;
    private double price;
    @Transient
    private int trip_id;
    private String picture;
    private String description;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
