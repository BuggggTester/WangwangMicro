package com.example.wangwangmicro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.wangwangmicro.dao")
public class wangwangMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(wangwangMicroApplication.class, args);
	}

}
