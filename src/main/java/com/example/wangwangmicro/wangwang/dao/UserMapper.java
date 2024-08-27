package com.example.wangwangmicro.wangwang.dao;

import com.example.wangwang.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> selectAllUsers();
    @Select("select * from user order by create_time ")
    List<User> selectUsersByTime();
    @Select("select * from user order by user_name")
    List<User> selectUsersByName();
    @Insert("insert into user (age, password, user_name, mail,sex, avatar) VALUES (-1, #{password}, #{user_name},#{mail},#{sex},'file/avatar/default.png')")
    void createUser(String password, String user_name, String mail,int sex);
    @Select("select * from user where user_id = #{user_id}")
    User selectUserById(int user_id);
    @Select("select * from user where mail = #{mail}")
    User selectUserByMail(String mail);
    @Select("select count(*) from user where mail = #{mail}")
    int countUserByMail(String mail);
    @Select("select user_id from user order by user_id desc limit 1")
    int selectMaxId();
    @Update("""
                    update user
                    set
                        age = #{age}, user_name = #{username}, identity = #{identity}, avatar = #{avatar}, mail = #{mail},
                        sex = #{sex}, password = #{password}
                    where user_id = #{userid}
            """)
    void updateUserById(User user);
    @Update("update user set avatar = #{fileNewName} where user_id = #{userId}")
    void updateAvatarById(String fileNewName, int userId);
    @Delete("delete from user where user_id = #{user_id}")
    void deleteUserById(int user_id);
}
