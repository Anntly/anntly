package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.order.dto.Stock;
import com.anntly.shop.dto.FoodDto;
import com.anntly.shop.dto.Node;
import com.anntly.shop.dto.OrderDto;
import com.anntly.shop.dto.OrderFood;
import com.anntly.shop.pojo.MenuFood;
import com.anntly.shop.service.MenuFoodService;
import com.anntly.shop.vo.MenuFoodParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author soledad
 * @Title: MenuFoodController
 * @ProjectName anntly
 * @Description: 菜单菜品管理Controller
 * @date 2019/2/411:18
 */
@RestController
@RequestMapping("/mfood")
public class MenuFoodController {

    @Autowired
    private MenuFoodService menuFoodService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('QUERY_MENU')")
    @ApiOperation(value="获取餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<PageResult<MenuFood>> queryMenuPage(PageRequest pageRequest){
        MenuFoodParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), MenuFoodParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(menuFoodService.queryPage(pageRequest,params));
    }

    @PutMapping("/saleable/{id}")
    @PreAuthorize("hasAuthority('UPDATE_MENU')")
    @ApiOperation(value="根据ID修改菜品上下架", notes="无")
    public ResponseEntity<Void> changeSalable(@PathVariable("id") Long id){
        // TODO 使用redis或者其他方式或者前端进行限流
        menuFoodService.changeSalable(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/recommend/{id}")
    @PreAuthorize("hasAuthority('UPDATE_MENU')")
    @ApiOperation(value="根据ID修改菜品是否为推荐菜品", notes="无")
    public ResponseEntity<Void> changeRecommend(@PathVariable("id") Long id){
        // TODO 使用redis或者其他方式或者前端进行限流
        menuFoodService.changeRecommend(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_MENU')")
    @ApiOperation(value="增添餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> saveRestaurant(MenuFood menuFood){
        // TODO 需要与餐厅Id相绑定
        if(null == menuFood){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuFoodService.saveMenuFood(menuFood);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_MENU')")
    @ApiOperation(value="修改餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> updateRestaurant(MenuFood menuFood){
        // TODO 需要与餐厅Id相绑定
        if(null == menuFood){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuFoodService.updateMenuFood(menuFood);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_MENU')")
    @ApiOperation(value="删除单个菜品", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteFood(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuFoodService.deleteFood(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @PreAuthorize("hasAuthority('UPDATE_MENU')")
    @ApiOperation(value="批量删除菜品", notes="无")
    public ResponseEntity<Void> deleteFood(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuFoodService.deleteFoods(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/stock/reduce",method = RequestMethod.GET)
    @ApiOperation(value="根据id列表获取价格折扣库存", notes="无")
    public ResponseEntity<Void> rudeceStock(@RequestParam("stocks") String stocks){
        if(null == stocks){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        List<Stock> stockList = JsonUtils.parseList(stocks, Stock.class);
        menuFoodService.rudeceStock(stockList);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/ids")
    @ApiOperation(value="根据id列表获取价格折扣库存", notes="无")
    public ResponseEntity<List<FoodDto>> queryFoodsByIds(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(menuFoodService.queryFoodsByIds(ids));
    }

    @GetMapping("/nodes")
    @ApiOperation(value="根据分类mCid查询nodes", notes="无")
    public ResponseEntity<List<FoodDto>> queryNodesByCid(@RequestParam("mCid") Long mCid){
        if(null == mCid){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(menuFoodService.queryNodesByCid(mCid));
    }

    @GetMapping("/orders")
    @ApiOperation(value="根据菜单Id查询下单信息", notes="无")
    public ResponseEntity<List<OrderDto>> queryOrderDtosByMenuId(@RequestParam("menuId") Long menuId){
        if(null == menuId){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(menuFoodService.queryOrderDtosByMenuId(menuId));
    }

    @GetMapping("/recommended")
    @ApiOperation(value="获取推荐菜品", notes="无")
    public ResponseEntity<List<OrderFood>> queryRecommendedFoods(@RequestParam("menuId") Long menuId){
        if(null == menuId){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(menuFoodService.queryRecommendedFoods(menuId));
    }
}
