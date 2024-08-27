package com.example.wangwangmicro.wangwang.service.impl;

import com.example.wangwang.dao.UserMapper;
import com.example.wangwang.entity.User;
import com.example.wangwang.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public void createUser(String password, String user_name, String mail,int sex) {
        userMapper.createUser(password, user_name, mail,sex);
    }

    @Override
    public User selectUserById(int user_id) {
        return userMapper.selectUserById(user_id);
    }

    @Override
    public User selectUserByMail(String mail) {
        return userMapper.selectUserByMail(mail);
    }

    @Override
    public int countUserByMail(String mail) {
        return userMapper.countUserByMail(mail);
    }

    @Override
    public int selectMaxId() {
        return userMapper.selectMaxId();
    }

    @Override
    public void updateUserById(User user) {
        userMapper.updateUserById(user);
    }

    @Override
    public void deleteUserById(int user_id) {
        userMapper.deleteUserById(user_id);
    }

    @Override
    public List<User> selectUsersByTime() {
        return userMapper.selectUsersByTime();
    }

    @Override
    public List<User> selectUsersByName() {
        return userMapper.selectUsersByName();
    }

    @Override
    public void updateAvatarById(String fileNewName, int userId) {
        userMapper.updateAvatarById(fileNewName, userId);
    }
}
