package com.anntly.dish.service;

import com.anntly.common.vo.PageResult;
import com.anntly.dish.pojo.Food;

import java.util.List;


public interface FoodService {
    PageResult<Food> queryFoodPage(String key, Boolean saleable, String sortBy, Boolean desc, Integer page, Integer rows);

    void changeSalable(Long id);

    void createFood(Food food);

    void updateFood(Food food);

    void deleteFood(Long id);

    void deleteFoods(List<Long> ids);
}
