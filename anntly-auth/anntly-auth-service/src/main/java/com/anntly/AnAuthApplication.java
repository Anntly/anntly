package com.anntly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author soledad
 * @Title: AnAuthApplication
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/1816:27
 */
@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.anntly.auth.mapper")
public class AnAuthApplication {

    public static void main(String[] args){
        SpringApplication.run(AnAuthApplication.class, args);
    }

}
