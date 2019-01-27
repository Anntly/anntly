package com.anntly.shop.service.impl;

import com.anntly.shop.mapper.RestaurantMapper;
import com.anntly.shop.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author soledad
 * @Title: RestaurantServiceImpl
 * @ProjectName anntly
 * @Description: RestaurantServiceImpl
 * @date 2019/1/2421:17
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;
}
