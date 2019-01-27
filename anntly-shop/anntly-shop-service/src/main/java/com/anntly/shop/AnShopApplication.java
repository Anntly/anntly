package com.anntly.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author soledad
 * @Title: AnShopApplication
 * @ProjectName anntly
 * @Description: Shop微服务启动类
 * @date 2019/1/2415:56
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.anntly.shop.mapper")
public class AnShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnShopApplication.class,args);
    }
}
