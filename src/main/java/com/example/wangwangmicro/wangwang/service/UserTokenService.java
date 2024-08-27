package com.example.wangwangmicro.wangwang.service;

import com.example.wangwang.entity.UserToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.Date;

public interface UserTokenService {
    UserToken selectByToken(String token);
    void insertToken(String token, int user_id, Timestamp update_time, Timestamp expire_time);
    void updateToken(String token, int user_id, Date update_time, Date expire_time);
    int searchIfExist(int user_id);
}
