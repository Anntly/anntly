package com.anntly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.anntly.dish.mapper")
public class AnDishApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnDishApplication.class,args);
    }
}
