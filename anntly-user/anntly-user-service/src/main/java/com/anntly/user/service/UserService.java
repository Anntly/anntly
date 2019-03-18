package com.anntly.user.service;

import com.anntly.user.dto.UserInfoDto;
import com.anntly.user.pojo.AuthToken;
import com.anntly.user.pojo.User;
import com.anntly.user.pojo.UserToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author soledad
 * @Title: UserService
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2111:16
 */
public interface UserService {
    void saveUser(String username,String password);

    UserToken login(String username, String password, String clientId, String clientSecret);

    User queryUser(String username);

    AuthToken getUserToken(String token);

    boolean removeToken(String accessToken);

    AuthToken reLogin(String refresh_token, String clientId, String clientSecret);

    void changePassword(Map<String, String> pass, HttpServletRequest request);

    Boolean checkUser(String username, String phone, String email);

    UserInfoDto getUserInfo(String username);

    void updateUserInfo(UserInfoDto userInfoDto);
}
