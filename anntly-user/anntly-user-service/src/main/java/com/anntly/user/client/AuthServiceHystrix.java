package com.anntly.user.client;

import com.anntly.user.pojo.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        log.warn("Fallback of getToken is executed");
        return null;
    }

    @Override
    public JWT refreshToken(String authorization, String type, String refresh_token) {
        log.warn("Fallback of getToken is executed");
        return null;
    }
}