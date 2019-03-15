package com.anntly.order.utils;

import com.anntly.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author soledad
 * @Title: Oauth2Util
 * @ProjectName anntly
 * @Description: Header获取解析token
 * @date 2019/2/2522:14
 */
@Slf4j
public class Oauth2Util {

    public static Map<String,Object> getJwtClaimsFromHeader(HttpServletRequest request) {
        if (request == null) {
            log.error("未获取到request");
            return null;
        } //取出头信息
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || authorization.indexOf("Bearer") < 0) {
            log.error("未获取到 Authorization");
            return null;
        } //从Bearer 后边开始取出token
        String token = authorization.substring(7);
        Map<String,Object> map = null;
        try {
            //解析jwt
            Jwt decode = JwtHelper.decode(token);
            //得到 jwt中的用户信息
            String claims = decode.getClaims();
            //将jwt转为Map
            map = JsonUtils.parse(claims, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        } return map;
    }
}
