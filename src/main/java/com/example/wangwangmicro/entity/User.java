package com.example.wangwangmicro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Entity
@Component
@Data
@Slf4j
public class User {
    @Id
    private int user_id;
    private int age;
    private int sex;
    private String password;
    private String user_name;
    private String identity;
    private String avatar;
    private String mail;
    private Timestamp create_time;
    @Transient
    private String new_pwd;
}
