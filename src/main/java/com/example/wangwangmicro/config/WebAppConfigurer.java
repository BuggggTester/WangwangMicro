package com.example.wangwangmicro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
                .allowCredentials(false)
                .maxAge(3600);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/avatar/**").addResourceLocations("file:" + System.getProperty("user.dir")+"/images/avatars/");
        registry.addResourceHandler("/api/avatar/**").addResourceLocations("");
        registry.addResourceHandler("/file/hotels/**").addResourceLocations("file:" + System.getProperty("user.dir")+ "/images/hotels/");
        registry.addResourceHandler("/file/foods/**").addResourceLocations("file:" + System.getProperty("user.dir") + "/images/foods/");
        registry.addResourceHandler("/file/rooms/**").addResourceLocations("file:" + System.getProperty("user.dir") + "/images/rooms/");
    }
}
