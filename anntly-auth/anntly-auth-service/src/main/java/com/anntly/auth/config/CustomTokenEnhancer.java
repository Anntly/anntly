package com.anntly.auth.config;

import com.anntly.user.pojo.Permission;
import com.anntly.user.pojo.Role;
import com.anntly.user.pojo.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author soledad
 * @Title: CustomTokenEnhancer
 * @ProjectName anntly
 * @Description: 添加用户头像，权限等信息
 * @date 2019/2/2316:44
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        User user = (User) oAuth2Authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("userId",user.getId());
        additionalInfo.put("username",user.getUsername());
        additionalInfo.put("avatar",user.getIcon());
        List<Permission> permissions = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            permissions.addAll(role.getAuthorities());
        }
        additionalInfo.put("roles",permissions.stream().map(Permission::getName).collect(Collectors.toList()));
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
