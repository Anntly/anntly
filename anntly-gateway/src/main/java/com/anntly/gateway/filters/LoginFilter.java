package com.anntly.gateway.filters;

import com.anntly.gateway.service.AuthService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author soledad
 * @Title: LoginFilter
 * @ProjectName anntly
 * @Description: 身份校验过滤器
 * @date 2019/2/2416:35
 */
@Slf4j
@Component
public class LoginFilter extends ZuulFilter {


    @Autowired
    private AuthService authService;

    private List<String> allowPaths = Arrays.asList("/api/user/register",
            "/api/user/login","/api/user/info","/api/user/relogin");

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {

        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取request
        HttpServletRequest request = ctx.getRequest();

        // 获取请求的url路径
        String path = request.getRequestURI();
        // 判断是否放行，放心则返回false
        return !isAllowPath(path);
    }

    private boolean isAllowPath(String path){
        // 遍历白名单
        for (String allowPath : allowPaths) {
            // 判断是否允许
            if(path.startsWith(allowPath)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        //上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        //请求对象
        HttpServletRequest request = requestContext.getRequest();

        // 从http header查询jwt令牌是否存在,不存在则拦截
        String jwt = authService.getJwtFromHeader(request);
        if(jwt == null){
            //拒绝访问
            log.error("Header中token不存在");
            access_denied(requestContext,"notoken");
        }

        // 从cookie中查询用户身份令牌是否存在，不存在则拦截
        String access_token = authService.getTokenFromCookie(request);
        log.info("access_token:"+access_token);
        if(access_token == null){
            //拒绝访问
            log.error("cookie中令牌不存在");
            access_denied(requestContext,"noheader");
        }

        // 从Redis查询user_token令牌是否过期，过期则拦截
        long expire = authService.getExpire(access_token);
        if(expire<=0){
            //拒绝访问
            log.error("redis中token过期,ttl为"+expire);
            access_denied(requestContext,"noredis");
        }

        return null;
    }

    //拒绝访问
    private void access_denied(RequestContext requestContext,String data){
        requestContext.setSendZuulResponse(false);//拒绝访问
        //设置响应内容
//        ResponseResult responseResult =new ResponseResult(CommonCode.UNAUTHENTICATED);
//        String responseResultString = JSON.toJSONString(responseResult);
//        requestContext.setResponseBody();
//        //设置状态码
//        requestContext.setResponseStatusCode(200);
//        HttpServletResponse response = requestContext.getResponse();
//        response.setContentType("application/json;charset=utf‐8");
        requestContext.setResponseStatusCode(401);
        requestContext.setResponseBody(data);// 返回错误内容
    }
}
