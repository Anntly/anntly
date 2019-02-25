package com.anntly.user.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.CookieUtil;
import com.anntly.user.dto.UserLoginDto;
import com.anntly.user.pojo.AuthToken;
import com.anntly.user.pojo.LoginRequest;
import com.anntly.user.pojo.User;
import com.anntly.user.pojo.UserToken;
import com.anntly.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author soledad
 * @Title: UserController
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2111:23
 */
@RestController
//@RequestMapping("/user")
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
        saveCookie(userToken.getAuthToken().getAccessToken());
        return ResponseEntity.ok(userToken.getUserLoginDto());
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
    public ResponseEntity<Void> userLogOut(String token){
        // 删除redis中的token
        boolean result = userService.removeToken(token);
        System.out.println("删除"+result);
        // 删除cookie中的token
        clearCookie(token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 将令牌存储在cookie
    private void saveCookie(String token){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
    }

    // 删除cookie
    private void clearCookie(String token){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,0,false);
    }

    @GetMapping(value = "/foo")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> getFoo() {
        return ResponseEntity.ok("i'm foo, " + UUID.randomUUID().toString());
    }
}
