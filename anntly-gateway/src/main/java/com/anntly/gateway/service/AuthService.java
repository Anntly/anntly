package com.anntly.gateway.service;

import com.anntly.common.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author soledad
 * @Title: AuthService
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2421:32
 */
@Service
public class AuthService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //冲头取出jwt令牌
    public String getJwtFromHeader(HttpServletRequest request){
        // 取出头信息
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)){
            return null;
        }
        if(!authorization.startsWith("Bearer ")){
            return null;
        }
        String jwt = authorization.substring(7);
        return jwt;
    }

    //从cookie中取出身份令牌
    public String getTokenFromCookie(HttpServletRequest request){
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
        String access_token = cookieMap.get("uid");
        if(StringUtils.isEmpty(access_token)){
            return null;
        } return access_token;
    }

    //从redis中判断令牌是否有效
    public long getExpire(String access_token) {
        //token在redis中的key
        String key = "user_token:"+access_token;
        Long expire = stringRedisTemplate.getExpire(key);
        return expire;
    }
}
