package com.example.wangwangmicro.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Data
@Slf4j
@Component
public class Passenger {
    private int passenger_id;
    private String identity;
    private BigInteger phone_number;
    private String name;
    private int user_id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
