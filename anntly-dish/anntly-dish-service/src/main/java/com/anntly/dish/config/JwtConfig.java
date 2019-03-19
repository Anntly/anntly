package com.anntly.dish.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

/**
 * @author soledad
 * @Title: JwtConfig
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/219:04
 */
@Configuration
public class JwtConfig {

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

//    @Autowired
//    private CustomAccessTokenConverter customAccessTokenConverter;

    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * 公钥解密密钥
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer(){
        JwtAccessTokenConverter converter= new JwtAccessTokenConverter ();
        Resource resource= new ClassPathResource("public.cert");
        String  publicKey;
        try {
            publicKey=new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        converter.setVerifierKey(publicKey);
//        converter.setAccessTokenConverter(customAccessTokenConverter);
        return converter;
    }

}
