package com.example.wangwangmicro.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
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
    private int user_id;
    private String token;
    private Timestamp update_time;
    private Timestamp expire_time;
}
