package com.example.wangwangmicro.service;

import com.example.wangwangmicro.entity.UserToken;

import java.sql.Timestamp;
import java.util.Date;

public interface UserTokenService {
    UserToken selectByToken(String token);
    void insertToken(String token, int user_id, Timestamp update_time, Timestamp expire_time);
    void updateToken(String token, int user_id, Date update_time, Date expire_time);
    int searchIfExist(int user_id);
}
