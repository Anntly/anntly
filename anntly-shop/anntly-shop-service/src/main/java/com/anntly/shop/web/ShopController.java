package com.anntly.shop.web;

import com.anntly.shop.pojo.Restaurant;
import com.anntly.shop.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author soledad
 * @Title: ShopController
 * @ProjectName anntly
 * @Description: 商家Controller
 * @date 2019/1/2420:23
 */
@RestController
@RequestMapping("/restaurant")
public class ShopController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/page")
    public ResponseEntity<List<Restaurant>> queryRestaurantPage(){
        return null;
    }
}
