package com.anntly.shop.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.RestaurantNode;
import com.anntly.shop.pojo.Restaurant;
import com.anntly.shop.vo.RestaurantParams;
import com.anntly.shop.dto.RestaurantDto;

import java.util.List;


/**
 * @author soledad
 * @Title: RestaurantService
 * @ProjectName anntly
 * @Description: RestaurantService
 * @date 2019/1/2421:07
 */
public interface RestaurantService {
    PageResult<RestaurantDto> queryPage(PageRequest pageRequest, RestaurantParams params, Long userId);

    void saveRestaurant(Restaurant restaurant);

    void updateRestaurant(Restaurant restaurant);

    void deleteRestaurant(Long id);

    void deleteRestaurants(List<Long> ids);

    List<RestaurantNode> queryNodes(Long userId);
}
