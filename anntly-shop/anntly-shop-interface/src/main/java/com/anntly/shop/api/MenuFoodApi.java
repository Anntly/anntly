package com.anntly.shop.api;

import com.anntly.order.dto.Stock;
import com.anntly.shop.dto.FoodDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author soledad
 * @Title: MenuFoodApi
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/914:58
 */
public interface MenuFoodApi {

    @RequestMapping(value = "/mfood/stock/reduce",method = RequestMethod.GET)
    void rudeceStock(@RequestParam("stocks") String stocks);

    @GetMapping("/mfood/ids")
    List<FoodDto> queryFoodsByIds(@RequestParam("ids") List<Long> ids);
}
