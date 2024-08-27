package com.example.wangwangmicro.service;

import com.example.wangwangmicro.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    List<User> selectAllUsers();
    void createUser(String password, String user_name, String mail,int sex);
    User selectUserById(int user_id);
    User selectUserByMail(String mail);
    int countUserByMail(String mail);
    int selectMaxId();
    void updateUserById(User user);
    void deleteUserById(int user_id);
    List<User> selectUsersByTime();

    List<User> selectUsersByName();

    void updateAvatarById(String fileNewName, int userId);

}
