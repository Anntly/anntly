package com.anntly.shop.service.impl;


import com.anntly.shop.pojo.MenuFood;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author soledad
 * @Title: MenuFoodServiceImplTest
 * @ProjectName anntly
 * @Description: TODO
 * @date 2019/2/1416:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuFoodServiceImplTest {

    @Autowired
    private MenuFoodServiceImpl menuFoodService;

    @Test
    public void test1(){
        List<Long> ids = Arrays.asList(3L,7L,2L);
        List<Long> menuFoods = menuFoodService.queryFoodsByCid(ids);
        System.out.println("获得"+menuFoods.size());
    }
}
