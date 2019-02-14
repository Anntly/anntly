package com.anntly.shop.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.pojo.MenuFood;
import com.anntly.shop.vo.MenuFoodParams;

import java.util.List;

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
}
