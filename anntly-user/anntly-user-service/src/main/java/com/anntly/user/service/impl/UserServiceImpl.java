package com.anntly.user.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.user.client.AuthServiceClient;
import com.anntly.user.dto.UserLoginDto;
import com.anntly.user.mapper.UserMapper;
import com.anntly.user.pojo.*;
import com.anntly.user.service.UserService;
import com.anntly.user.utils.BPwdEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author soledad
 * @Title: UserServiceImpl
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2111:17
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthServiceClient client;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${auth.tokenValiditySeconds}")
    private long tokenValiditySeconds; // 过期时间

    @Override
    public void saveUser(String username,String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        user.setRegisterTime(new Date());
        user.setUpdateTime(user.getRegisterTime());
        user.setLastLoginTime(user.getRegisterTime());
        user.setDataStatus(true);
        user.setUserStatus(1); //暂时先设置为1
        userMapper.insert(user);
    }

    // 从认证服务申请令牌，并且将token存储在redis与cookie中
    @Override
    public UserToken login(String username, String password, String clientId, String clientSecret) {
        // 查询数据库
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            log.error("用户名错误");
            throw new AnnException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        if(!BPwdEncoderUtil.matches(password,user.getPassword())){
            log.error("密码不匹配");
            throw new AnnException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        String httpbasic = getHttpBasic(clientId,clientSecret);
        // 从auth-service获取JWT
        JWT jwt = client.getToken(httpbasic, "password", username, password);
        if(jwt == null){
            log.error("token获取错误");
            throw new AnnException(ExceptionEnum.LOGIN_FAILED);
        }

        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(jwt.getJti()); //用户身份令牌，返回用户短的，再去redis查询长的
        authToken.setRefresh_token(jwt.getRefresh_token()); //刷新令牌
        authToken.setJwt_token(jwt.getAccess_token()); //jwt令牌

        // 将令牌存储在redis
        boolean isToken = saveToken(authToken.getAccessToken(), JsonUtils.serialize(authToken),tokenValiditySeconds);
        if(!isToken){
            throw new AnnException(ExceptionEnum.LOGIN_FAILED);
        }

        UserToken userToken = new UserToken();
        userToken.setAuthToken(authToken);

        UserLoginDto userLoginDto =  new UserLoginDto();
        userLoginDto.setUsername(username);
        userLoginDto.setToken(authToken.getAccessToken());
        userLoginDto.setAvatar(user.getIcon());
        userLoginDto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        userToken.setUserLoginDto(userLoginDto);
        return userToken;
    }

    @Override
    public User queryUser(String username) {
        // 查询数据库
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            log.error("用户名或密码错误");
            throw new AnnException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public AuthToken getUserToken(String token) {
        String key = "user_token:" + token;
        String value = redisTemplate.opsForValue().get(key);
        // 转换为AuthToken对象
        AuthToken authToken = JsonUtils.parse(value, AuthToken.class);
        return authToken;
    }


    /**
     * 存储令牌到redis
     * @param accessToken 用户身份令牌 JTI
     * @param content AuthToken对象的内容
     * @param ttl 过期时间
     * @return
     */
    private boolean saveToken(String accessToken,String content,long ttl){
        String key = "user_token:" + accessToken;
        redisTemplate.boundValueOps(key).set(content,ttl, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire(key,TimeUnit.SECONDS);
        return expire > 0;
    }

    /**
     * 删除token
     * @param accessToken
     * @return
     */
    @Override
    public boolean removeToken(String accessToken){
        String key = "user_token:" + accessToken;
        redisTemplate.delete(key);
        Long expire = redisTemplate.getExpire(key,TimeUnit.SECONDS);
        return true;
    }

    //获取httpbasic的串
    private String getHttpBasic(String clientId,String clientSecret){
        String str = clientId+ ":" + clientSecret;
        // 将串进行base64加密
        byte[] encode = Base64Utils.encode(str.getBytes());
        return "Basic "+new String(encode);
    }
}
