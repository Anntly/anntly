package com.anntly.order.utils;

import com.anntly.common.pojo.UserInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author soledad
 * @Title: AnOauth2Utils
 * @ProjectName anntly
 * @Description: 返回User信息
 * @date 2019/2/2522:25
 */
@Slf4j
public class AnOauth2Utils {

    public UserInfo getUserJwtFromHeader(HttpServletRequest request){
        Map<String, Object> jwtClaims = Oauth2Util.getJwtClaimsFromHeader(request);
        if(jwtClaims == null || null == jwtClaims.get("userId")){
            log.error("未获取到 id");
            return null;
        }
        UserInfo userJwt = new UserInfo();
        Long id = Long.parseLong(String.valueOf(jwtClaims.get("userId")));

        userJwt.setId(id);
        userJwt.setUsername(String.valueOf(jwtClaims.get("username")));
        return userJwt;
    }
}
