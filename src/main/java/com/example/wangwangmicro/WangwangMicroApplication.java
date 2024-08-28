package com.example.wangwangmicro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.wangwangmicro.dao")
@EnableFeignClients
public class WangwangMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(WangwangMicroApplication.class, args);
	}

}
