package com.example.wangwangmicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WangwangMicroApplication {
	public static void main(String[] args) {
		SpringApplication.run(WangwangMicroApplication.class, args);
	}
}
