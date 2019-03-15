package com.anntly.coupons.api;

import com.anntly.coupons.pojo.Coupons;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author soledad
 * @Title: CouponsApi
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1015:20
 */
public interface CouponsApi {

    @GetMapping("/restaurant/{id}")
    Coupons queryCouponsById(@PathVariable("id") String id);
}
