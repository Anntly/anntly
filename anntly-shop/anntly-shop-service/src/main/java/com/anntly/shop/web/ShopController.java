package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.pojo.UserInfo;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.RestaurantNode;
import com.anntly.shop.pojo.Restaurant;
import com.anntly.shop.service.RestaurantService;
import com.anntly.shop.utils.AnOauth2Utils;
import com.anntly.shop.vo.RestaurantParams;
import com.anntly.shop.dto.RestaurantDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @PreAuthorize("hasAuthority('QUERY_SHOP')")
    @ApiOperation(value="获取餐厅列表", notes="命名需要与数据库对应")
    public ResponseEntity<PageResult<RestaurantDto>> queryRestaurantPage(HttpServletRequest request,
                                                                         PageRequest pageRequest){
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
        return ResponseEntity.ok(restaurantService.queryPage(request,pageRequest,params));
    }

    @GetMapping("/res")
    @ApiOperation(value="获取餐厅下拉选项", notes="命名需要与数据库对应")
    public ResponseEntity<List<RestaurantNode>> queryNodes(HttpServletRequest request){
        // 获取登录用户
        AnOauth2Utils anOauth2Utils = new AnOauth2Utils();
        UserInfo user = anOauth2Utils.getUserJwtFromHeader(request);
        // TODO 需要与userId相绑定，目前先查询出所有店铺
        // Long userId = 1L;
        return ResponseEntity.ok(restaurantService.queryNodes(user.getId()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_SHOP')")
    @ApiOperation(value="上传菜品", notes="命名需要与数据库对应")
    public ResponseEntity<Void> saveRestaurant(Restaurant restaurant,HttpServletRequest request){
        // TODO 需要与userId相绑定
        //Long userId = 1L;
        AnOauth2Utils anOauth2Utils = new AnOauth2Utils();
        UserInfo user = anOauth2Utils.getUserJwtFromHeader(request);
        if(null == restaurant){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        restaurant.setUserId(user.getId());
        restaurantService.saveRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_SHOP')")
    @ApiOperation(value="更新菜品", notes="命名需要与数据库对应")
    public ResponseEntity<Void> updateRestaurant(Restaurant restaurant){
        if(null == restaurant){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        restaurantService.updateRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REMOVE_SHOP')")
    @ApiOperation(value="删除单个餐厅", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("id") Long id){
        // TODO 待定不删除餐厅下的所有房间，餐桌
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @PreAuthorize("hasAuthority('REMOVE_SHOP')")
    @ApiOperation(value="批量删除餐厅", notes="无")
    public ResponseEntity<Void> deleteFood(@RequestParam("ids") List<Long> ids){
        // TODO 待定不删除餐厅下的所有房间，餐桌
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        restaurantService.deleteRestaurants(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
