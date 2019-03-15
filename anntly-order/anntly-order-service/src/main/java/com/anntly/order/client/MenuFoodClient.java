package com.anntly.order.client;

import com.anntly.shop.api.MenuFoodApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author soledad
 * @Title: MenuFoodClient
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/915:03
 */
@FeignClient("shop-service")
public interface MenuFoodClient extends MenuFoodApi {

}
