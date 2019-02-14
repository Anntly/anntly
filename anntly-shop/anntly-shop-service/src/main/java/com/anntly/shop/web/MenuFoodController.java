package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.pojo.MenuFood;
import com.anntly.shop.service.MenuFoodService;
import com.anntly.shop.vo.MenuFoodParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value="根据ID修改菜品上下架", notes="无")
    public ResponseEntity<Void> changeSalable(@PathVariable("id") Long id){
        // TODO 使用redis或者其他方式或者前端进行限流
        menuFoodService.changeSalable(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/recommend/{id}")
    @ApiOperation(value="根据ID修改菜品是否为推荐菜品", notes="无")
    public ResponseEntity<Void> changeRecommend(@PathVariable("id") Long id){
        // TODO 使用redis或者其他方式或者前端进行限流
        menuFoodService.changeRecommend(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
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
    @ApiOperation(value="删除单个菜品", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteFood(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuFoodService.deleteFood(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @ApiOperation(value="批量删除菜品", notes="无")
    public ResponseEntity<Void> deleteFood(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuFoodService.deleteFoods(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
