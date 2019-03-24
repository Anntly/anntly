package com.anntly.coupons.web;

import com.anntly.common.pojo.UserInfo;
import com.anntly.coupons.dto.CouponsDto;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.service.CouponsService;
import com.anntly.coupons.utils.AnOauth2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author soledad
 * @Title: UserCouponsController
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/2413:53
 */
@RestController
@RequestMapping("/user")
public class UserCouponsController {

    @Autowired
    private CouponsService couponsService;

    @GetMapping("/restaurant")
    public ResponseEntity<List<Coupons>> queryCouponsByResaurantId(
            @RequestParam("restaurantId") Long restaurantId){

        return ResponseEntity.ok(couponsService.queryCouponsByResaurantId(restaurantId));
    }

    @PostMapping("/receive")
    public ResponseEntity<Void> receiveCoupon(HttpServletRequest request,
                                              @RequestParam("couponsId") String couponsId,
                                              @RequestParam("restaurantId") Long restaurantId){
        // 获取用户信息
        AnOauth2Utils anOauth2Utils = new AnOauth2Utils();
        UserInfo user = anOauth2Utils.getUserJwtFromHeader(request);
        couponsService.receiveCoupon(user.getId(),couponsId,restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/mycoupons")
    public ResponseEntity<Map<String,List<CouponsDto>>> queryMyCoupons(HttpServletRequest request,
                                                                       @RequestParam("totalFee")BigDecimal totalFee){
        AnOauth2Utils anOauth2Utils = new AnOauth2Utils();
        UserInfo user = anOauth2Utils.getUserJwtFromHeader(request);
        return ResponseEntity.ok(couponsService.queryMyCoupons(user.getId(),totalFee));
    }
}
