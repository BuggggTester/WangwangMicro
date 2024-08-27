package com.example.wangwangmicro.service.impl;

import com.example.wangwangmicro.dao.UserTokenMapper;
import com.example.wangwangmicro.entity.UserToken;
import com.example.wangwangmicro.service.UserTokenService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service("userTokenService")
public class UserTokenServiceImpl implements UserTokenService {
    @Resource
    private UserTokenMapper userTokenMapper;
    @Override
    public UserToken selectByToken(String token) {
        return userTokenMapper.selectByToken(token);
    }

    @Override
    public void insertToken(String token, int user_id, Timestamp update_time, Timestamp expire_time) {
        userTokenMapper.insertToken(token, user_id, update_time, expire_time);
    }

    @Override
    public void updateToken(String token, int user_id, Date update_time, Date expire_time) {
        userTokenMapper.updateToken(token, user_id, update_time, expire_time);
    }

    @Override
    public int searchIfExist(int user_id) {
        return userTokenMapper.searchIfExist(user_id);
    }
}
