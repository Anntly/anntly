package com.anntly.shop.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.order.dto.Stock;
import com.anntly.shop.dto.FoodDto;
import com.anntly.shop.dto.Node;
import com.anntly.shop.dto.OrderDto;
import com.anntly.shop.dto.OrderFood;
import com.anntly.shop.pojo.MenuFood;
import com.anntly.shop.vo.MenuFoodParams;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author soledad
 * @Title: MenuFoodService
 * @ProjectName anntly
 * @Description: MenuFoodService
 * @date 2019/2/1211:23
 */
public interface MenuFoodService {
    PageResult<MenuFood> queryPage(PageRequest pageRequest, MenuFoodParams params);

    void changeSalable(Long id);

    void changeRecommend(Long id);

    void saveMenuFood(MenuFood menuFood);

    void updateMenuFood(MenuFood menuFood);

    void deleteFood(Long id);

    void deleteFoods(List<Long> ids);

    List<Long> queryFoodsByCid(List<Long> cids);

    void rudeceStock(List<Stock> stocks);

    List<FoodDto> queryFoodsByIds(java.util.List<Long> ids);

    List<FoodDto> queryNodesByCid(Long mCid);

    List<OrderDto> queryOrderDtosByMenuId(Long menuId);

    List<OrderFood> queryRecommendedFoods(Long menuId);

    List<OrderDto> queryOrderDtosByDeskId(Long deskId);
}
