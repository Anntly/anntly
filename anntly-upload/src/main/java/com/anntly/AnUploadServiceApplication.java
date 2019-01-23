package com.anntly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author soledad
 * @Title: AnUploadServiceApplication
 * @ProjectName anntly
 * @Description: 启动类
 * @date 2019/1/2015:56
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AnUploadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnUploadServiceApplication.class,args);
    }
}
