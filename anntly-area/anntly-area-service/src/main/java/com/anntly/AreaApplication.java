package com.anntly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author soledad
 * @Title: AreaApplication
 * @ProjectName anntly
 * @Description: Area微服务启动类
 * @date 2019/2/112:20
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.anntly.area.mapper")
public class AreaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AreaApplication.class,args);
    }
}
