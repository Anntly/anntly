package com.anntly.shop.vo.ma;


import com.anntly.AnShopApplication;
import com.anntly.shop.dto.RestaurantDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author soledad
 * @Title: RestaurantVoTest
 * @ProjectName anntly
 * @Description: TODO
 * @date 2019/1/2823:40
 */
@SpringBootTest(classes = AnShopApplication.class)
@RunWith(SpringRunner.class)
public class RestaurantVoTest {


    @Test
    public void testVo(){
        RestaurantDto restaurantVo = new RestaurantDto();
        restaurantVo.setPid(1);
        restaurantVo.setCid(2);
        restaurantVo.setAid(3);
        restaurantVo.setLicense("1,2,3,4,5,6");
        restaurantVo.setPhoto("1,2,3,4,5,6");
        restaurantVo.initDatas();
        System.err.println(restaurantVo.getIds());
        System.err.println(restaurantVo.getPhotos());
        System.err.println(restaurantVo.getLicenses());
    }

}
