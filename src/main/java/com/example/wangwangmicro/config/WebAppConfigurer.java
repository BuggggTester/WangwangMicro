package com.example.wangwangmicro.config;

import com.example.wangwangmicro.config.handler.TokenToUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Autowired
    private TokenToUserMethodArgumentResolver tokenToUserMethodArgumentResolver;
    /*
tokenToUserMethodArgumentResolver 注解处理方法
 */
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenToUserMethodArgumentResolver);
    }
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
