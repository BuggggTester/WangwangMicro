package com.example.wangwangmicro.entity;

import javax.persistence.Transient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@Slf4j
public class User {
    private int userid;
    private int age;
    private int sex;
    private String password;
    private String username;
    private String identity;
    private String avatar;
    private String mail;
    private Timestamp createtime;
    @Transient
    private String new_pwd;
}
