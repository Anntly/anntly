package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.RestaurantNode;
import com.anntly.shop.pojo.Restaurant;
import com.anntly.shop.service.RestaurantService;
import com.anntly.shop.vo.RestaurantParams;
import com.anntly.shop.dto.RestaurantDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "restaurant-controller",description = "餐厅查询接口")
public class ShopController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/page")
    @ApiOperation(value="获取餐厅列表", notes="命名需要与数据库对应")
    public ResponseEntity<PageResult<RestaurantDto>> queryRestaurantPage(PageRequest pageRequest){
        // TODO 需要与userId相绑定，目前先查询出所有店铺
        Long userId = 1L;
        RestaurantParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), RestaurantParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(restaurantService.queryPage(pageRequest,params,userId));
    }

    @GetMapping("/res")
    @ApiOperation(value="获取餐厅下拉选项", notes="命名需要与数据库对应")
    public ResponseEntity<List<RestaurantNode>> queryNodes(){
        // TODO 需要与userId相绑定，目前先查询出所有店铺
        Long userId = 1L;
        return ResponseEntity.ok(restaurantService.queryNodes(userId));
    }

    @PostMapping
    @ApiOperation(value="上传菜品", notes="命名需要与数据库对应")
    public ResponseEntity<Void> saveRestaurant(Restaurant restaurant){
        // TODO 需要与userId相绑定
        Long userId = 1L;
        if(null == restaurant){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        restaurant.setUserId(userId);
        restaurantService.saveRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @ApiOperation(value="更新菜品", notes="命名需要与数据库对应")
    public ResponseEntity<Void> updateRestaurant(Restaurant restaurant){
        // TODO 需要与userId相绑定
        Long userId = 1L;
        if(null == restaurant){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        restaurantService.updateRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="删除单个餐厅", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @ApiOperation(value="批量删除餐厅", notes="无")
    public ResponseEntity<Void> deleteFood(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        restaurantService.deleteRestaurants(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
