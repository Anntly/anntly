package com.anntly.order.web;

import com.anntly.common.pojo.UserInfo;
import com.anntly.common.vo.PageResult;
import com.anntly.order.dto.UserOrderDto;
import com.anntly.order.service.OrderService;
import com.anntly.order.utils.AnOauth2Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author soledad
 * @Title: UserOrderController
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/2111:05
 */
@RestController
@RequestMapping("/user")
public class UserOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @ApiOperation(value="根据用户Id以及过滤条件查询order", notes="无")
    public ResponseEntity<PageResult<UserOrderDto>> queryUserOrders(
            HttpServletRequest request,
            @RequestParam(value = "status",required = false) Integer status,
            @RequestParam(value = "type",required = false) Boolean type,
            @RequestParam(value = "payStatus",required = false) Boolean payStatus,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows){
        // 获取用户信息
        AnOauth2Utils anOauth2Utils = new AnOauth2Utils();
        UserInfo user = anOauth2Utils.getUserJwtFromHeader(request);

        return ResponseEntity.ok(orderService.queryUserOrders(user.getUsername(),status,type,payStatus,page,rows));
    }
}
