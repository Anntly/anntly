package com.anntly.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author soledad
 * @Title: GlobalMethodSecurityConfig
 * @ProjectName anntly
 * @Description: 开启方法级别的安全验证
 * @date 2019/2/219:11
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法级别安全验证
public class GlobalMethodSecurityConfig {
}
