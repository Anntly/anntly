package com.anntly.coupons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author soledad
 * @Title: CouponsApplication
 * @ProjectName anntly
 * @Description: 优惠券微服务启动类
 * @date 2019/3/321:25
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.anntly.coupons.mapper")
public class CouponsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponsApplication.class,args);
    }
}
