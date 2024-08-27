package com.example.wangwangmicro.dao;

import com.example.wangwangmicro.entity.UserToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.Date;

@Mapper
public interface UserTokenMapper {
    @Select("select * from user_token where token = #{token}")
    UserToken selectByToken(String token);
    @Insert("insert into user_token (user_id, token, update_time, expire_time) VALUES (#{user_id}, #{token}, #{update_time}, #{expire_time})")
    void insertToken(String token, int user_id, Timestamp update_time, Timestamp expire_time);
    @Update("update user_token set update_time = #{update_time}, expire_time = #{expire_time} where user_id = #{user_id}")
    void updateToken(String token, int user_id, Date update_time, Date expire_time);
    @Select("select count(*) from user_token where user_id = #{user_id}")
    int searchIfExist(int user_id);
}
