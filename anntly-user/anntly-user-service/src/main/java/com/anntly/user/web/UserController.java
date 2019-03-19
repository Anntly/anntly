package com.anntly.user.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.pojo.UserInfo;
import com.anntly.common.utils.CookieUtil;
import com.anntly.user.dto.UserInfoDto;
import com.anntly.user.dto.UserLoginDto;
import com.anntly.user.pojo.AuthToken;
import com.anntly.user.pojo.LoginRequest;
import com.anntly.user.pojo.User;
import com.anntly.user.pojo.UserToken;
import com.anntly.user.service.UserService;
import com.anntly.user.utils.AnOauth2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author soledad
 * @Title: UserController
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2111:23
 */
@RestController
public class UserController {

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void saveUser(@RequestParam("username") String username,
                         @RequestParam("password") String password){
        userService.saveUser(username,password);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDto> login(@RequestBody @Valid LoginRequest loginRequest) {
        UserToken userToken = userService.login(loginRequest.getUsername(), loginRequest.getPassword(), clientId, clientSecret);
        // 将token存储到cookie
        saveCookie(userToken.getAuthToken().getAccessToken(),userToken.getAuthToken().getRefresh_token());
        return ResponseEntity.ok(userToken.getUserLoginDto());
    }

    @PostMapping("/relogin")
    public ResponseEntity<AuthToken> reLogin(String refreshToken) {
        AuthToken authToken = userService.reLogin(refreshToken, clientId, clientSecret);
        // 将token存储到cookie
        saveCookie(authToken.getAccessToken(),authToken.getRefresh_token());
        return ResponseEntity.ok(authToken);
    }

//    @GetMapping("/query")
//    public ResponseEntity<User> queryUser(@RequestParam("username") String username){
//        return ResponseEntity.ok(userService.queryUser(username));
//    }


    @GetMapping("/info")
    public ResponseEntity<String> getInfo(@RequestParam(value = "token") String token){
        // 拿出身份令牌查询jwt令牌
        AuthToken authToken = userService.getUserToken(token);
        // 将jwt令牌返回给用户

        return ResponseEntity.ok(authToken.getJwt_token());
    }

    @PostMapping("/seeyou")
    public ResponseEntity<Void> userLogOut(String token,String refreshToken){
        // 删除redis中的token
        boolean result = userService.removeToken(token);
        System.out.println("删除"+result);
        // 删除cookie中的token
        clearCookie(token,refreshToken);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 将令牌存储在cookie
    private void saveCookie(String token,String refreshToken){
        // 需要存入refresh——token
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
        CookieUtil.addCookie(response,cookieDomain,"/","reid",refreshToken,cookieMaxAge,false);
    }

    // 删除cookie
    private void clearCookie(String token,String refreshToken){
        // 需要删除refresh——token
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,0,false);
        CookieUtil.addCookie(response,cookieDomain,"/","reid",refreshToken,0,false);
    }

//    @GetMapping(value = "/foo")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<String> getFoo() {
//        return ResponseEntity.ok("i'm foo, " + UUID.randomUUID().toString());
//    }

    @PutMapping("/changepass")
    @PreAuthorize("hasAuthority('CHANGE_PASS')")
    public ResponseEntity<Void> changePassword(@RequestBody Map<String,String> pass, HttpServletRequest request){
        if (CollectionUtils.isEmpty(pass) || pass.size() < 2) {
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        userService.changePassword(pass,request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/checkuser")
    public ResponseEntity<Boolean> checkUser(@RequestParam(value = "username",required = false) String username,
                                             @RequestParam(value = "phone",required = false) String phone,
                                             @RequestParam(value = "email",required = false) String email){
        return ResponseEntity.ok(userService.checkUser(username,phone,email));
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoDto> getUserInfo(HttpServletRequest request){
        // 获取登录用户
        AnOauth2Utils anOauth2Utils = new AnOauth2Utils();
        UserInfo info = anOauth2Utils.getUserJwtFromHeader(request);
        return ResponseEntity.ok(userService.getUserInfo(info.getUsername()));
    }

    @PutMapping("/changeinfo")
    @PreAuthorize("hasAuthority('CHANGE_INFO')")
    public ResponseEntity<Void> updateUserInfo(@RequestBody UserInfoDto userInfoDto){
        userService.updateUserInfo(userInfoDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
