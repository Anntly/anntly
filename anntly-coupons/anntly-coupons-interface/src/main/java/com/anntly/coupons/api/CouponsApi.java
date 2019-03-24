package com.anntly.coupons.api;

import com.anntly.coupons.pojo.Coupons;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author soledad
 * @Title: CouponsApi
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1015:20
 */
public interface CouponsApi {

    @GetMapping("/restaurant/usecoupon")
    Coupons queryCouponsById(@RequestParam("couponsId") String couponsId,
                             @RequestParam("userId") Long userId);
}
