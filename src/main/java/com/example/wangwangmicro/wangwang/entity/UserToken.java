package com.example.wangwangmicro.wangwang.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Component
@Entity
@Slf4j
public class UserToken {
    @Id
    private int userid;
    private String token;
    private Timestamp updatetime;
    private Timestamp expiretime;
}
