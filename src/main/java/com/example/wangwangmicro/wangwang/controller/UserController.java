package com.example.wangwangmicro.wangwang.controller;

import com.example.wangwang.config.annotation.TokenToUser;
import com.example.wangwang.dao.UserTokenMapper;
import com.example.wangwang.entity.common.R;
import com.example.wangwang.entity.User;
import com.example.wangwang.service.UserService;
import com.example.wangwang.service.UserTokenService;
import com.example.wangwang.util.JwtUtils;
import com.example.wangwang.util.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.example.wangwang.config.PathConfig.avatar;
import static com.example.wangwang.config.PathConfig.avatarUrl;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value ="/user")
public class UserController {
    @Resource
    private UserService userService;
    @Autowired
    private UserTokenService userTokenService;
    @PostMapping(value="/register")
    public R register(@RequestBody User user) {
        String password;
        String mail;
        //TODO: 获取数据
        if(!user.getPassword().isEmpty()) {
            password = user.getPassword();
        }else{
            return R.error("please input password");
        }
        if(user.getMail() != null && !user.getMail().isEmpty()) {
            mail = user.getMail();
        }else {
            return R.error("please input mail");
        }
        int user_id;
        //TODO: 验证用户信息合理性
        try {
            if (userService.countUserByMail(mail) != 0) {
                return R.error("mail already exists");
            }
            user_id = userService.selectMaxId() + 1;
        }catch (Exception e){
            return R.error(e.toString());
        }
        //正则验证
        //Md5加密
        String finalPwd = Md5Utils.agenerate(password);
        try {
            userService.createUser(finalPwd, user.getUsername(), mail, user.getSex());
            return R.ok("register success").put("user_id", user_id);
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
    @PostMapping(value="/login/admin")
    public R adminLogin(@RequestBody HashMap<String, String> userMap) {
        String mail;
        String password;
        if(userMap.containsKey("mail")) {
            mail = userMap.get("mail");
        }else {
            return R.error("please input mail");
        }
        if(userMap.containsKey("password")) {
            password = userMap.get("password");
        }else{
            return R.error("please input password");
        }
        try{
            User user = userService.selectUserByMail(mail);
            if(user.getMail().equals(mail) && Md5Utils.verify(password, user.getPassword()) && user.getIdentity().equals("administrator")) {
                return R.ok("login success").put("token", JwtUtils.createJWT(String.valueOf(user.getUserid()),user.getUsername(),60 * 60 * 1000));
            } else{
                return R.error("login failed");
            }
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
    @PostMapping(value="/login")
    public R login(@RequestBody HashMap<String, String> userMap) {
        String mail;
        String password;
        if(userMap.containsKey("mail")) {
            mail = userMap.get("mail");
        }else {
            return R.error("please input mail");
        }
        if(userMap.containsKey("password")) {
            password = userMap.get("password");
        }else{
            return R.error("please input password");
        }
        try{
            User user = userService.selectUserByMail(mail);
            if(user.getMail().equals(mail) && Md5Utils.verify(password, user.getPassword())) {
                LocalDateTime now = LocalDateTime.now();
                Timestamp update_time = Timestamp.valueOf(now);
                Timestamp expire_time = Timestamp.valueOf(now.plusDays(7));
                String token = JwtUtils.createJWT(String.valueOf(user.getUserid()),user.getUsername(),60 * 60 * 1000);
                userTokenService.insertToken(token,user.getUserid(),update_time,expire_time);
                return R.ok("login success").put("token", token);
            } else{
                return R.error("login failed");
            }
        }catch (DuplicateKeyException e1){
            return R.error("already login");
        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }
    @GetMapping(value="/getall")
    public R showAllUsers() {
        try{
            return R.ok().put("users",userService.selectAllUsers());
        }catch (Exception e) {
            return R.error(e.toString());
        }
    }
    @PostMapping(value="/update")
    public R updateUserInfo(@TokenToUser User origin, @RequestBody User user) {
//        //检查数据的合理性
//        if(!RegexValidation.isValidPassword(user.getPassword())) {
//            return R.error("error password");
//        }
//        if(user.getUsername().length()>50) {
//            return R.error("name too long");
//        }
//        if(user.getSex()!=0 && user.getSex()!=1) {
//            return R.error("error sex");
//        }
        try{
            if(user.getNew_pwd()!=null&&!user.getNew_pwd().isEmpty()) {
                user.setPassword(Md5Utils.agenerate(user.getNew_pwd()));
            }
            user.setUserid(origin.getUserid());
            if(user.getAge()==0){
                user.setAge(origin.getAge());
            }
            if(user.getUsername()==null || user.getUsername().isEmpty()) {
                user.setUsername(origin.getUsername());
            }
            user.setIdentity(origin.getIdentity());
            user.setAvatar(origin.getAvatar());
            if(user.getMail()==null || user.getMail().isEmpty()) {
                user.setMail(origin.getMail());
            }
            userService.updateUserById(user);
            return R.ok("update success");
        }catch (Exception e){
            return R.error(e.toString());
        }
    }
    @RequestMapping(value = "/update/avatar")
    public R updateAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("userId") int userId) {

        User user = userService.selectUserById(userId);

        String origin = user.getAvatar();
        if(!origin.equals("file/avatar/default.png")){
            File file1 = new File(avatarUrl + origin);
            file1.delete();//如果原来有头像，则删除
        }
        String filePath = avatarUrl;
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String fileNewName = UUID.randomUUID() + fileType;
        userService.updateAvatarById(avatar+fileNewName, userId);
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath+ fileNewName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            return R.error(e.toString());
        }
        return R.ok("上传成功！");
    }
    @GetMapping(value="/delete")
    public R deleteUser(@RequestParam("user_id")int user_id) {
        try{
            userService.deleteUserById(user_id);
            return R.ok("delete success");
        }catch (Exception e){
            return R.error("delete failed");
        }
    }
}
