package com.example.wangwangmicro.wangwang.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidation {

    public static void main(String[] args) {
        String input = "abc1234"; // 测试字符串
        if (isValidPassword(input)) {
            System.out.println("密码有效");
        } else {
            System.out.println("密码无效");
        }
    }

    public static boolean isValidPassword(String password) {

        String regex = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\s]+$).{6,30}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
