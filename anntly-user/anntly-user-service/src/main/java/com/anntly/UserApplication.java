package com.anntly;

import com.anntly.common.interceptor.FeignClientInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author soledad
 * @Title: UserApplication
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/1917:51
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.anntly.user.mapper")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

    // 添加feign远程拦截器 添加header信息
//    @Bean
//    public FeignClientInterceptor feignClientInterceptor(){
//        return new FeignClientInterceptor();
//    }
}
