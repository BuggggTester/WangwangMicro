package com.example.wangwangmicro.entity.food;

//import com.example.demo.common.constant.OrderStatus;
//import com.example.demo.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity
@Data
@Slf4j
@Component
public class FoodReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Transient
    private int user_id;
    @Transient
    private int food_id;
    private int quantity = 0;

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    private Food food;
    //@ManyToOne
    //@JoinColumn(name="user_id", referencedColumnName = "user_id")
    //private User user;
    @Transient
    private Timestamp order_time;
    //@Transient
    //private OrderStatus state;
    @Transient
    private int tid;
}
