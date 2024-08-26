package com.example.wangwangmicro;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ServletComponentScan
@Mapper
@EnableFeignClients
public class WangwangMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(WangwangMicroApplication.class, args);
	}

}
