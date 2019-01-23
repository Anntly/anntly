package com.anntly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul网关
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class AnGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnGateWayApplication.class,args);
    }
}
