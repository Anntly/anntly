package com.anntly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * Zuul网关
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class AnGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnGateWayApplication.class,args);
    }

}
