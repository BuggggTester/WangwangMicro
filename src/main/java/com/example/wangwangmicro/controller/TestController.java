package com.example.wangwangmicro.controller;

import com.example.wangwangmicro.config.annotation.TokenToUser;
import com.example.wangwangmicro.entity.User;
import com.example.wangwangmicro.entity.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value ="/test")
public class TestController {
    @GetMapping(value="/111")
    public R test1() {
        return R.ok("111");
    }
}
