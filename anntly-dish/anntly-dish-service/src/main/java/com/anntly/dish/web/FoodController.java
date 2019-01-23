package com.anntly.dish.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageResult;
import com.anntly.dish.pojo.Food;
import com.anntly.dish.service.FoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@Api(value = "dish-controller",description = "菜品查询接口")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/page")
    @ApiOperation(value="获取菜品列表", notes="命名需要与数据库对应")
    public ResponseEntity<PageResult<Food>> queryFoodPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable",required = false,defaultValue = "true") Boolean saleable,
            @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
            @RequestParam(value = "sortBy",required = false,defaultValue = "create_time") String sortBy,
            @RequestParam(value = "desc",required = false,defaultValue = "false") Boolean desc,
            @RequestParam(value = "rows",required = false,defaultValue = "10") Integer rows){
        return ResponseEntity.ok(foodService.queryFoodPage(key,saleable,sortBy,desc,page,rows));
    }

    @PutMapping("/saleable/{id}")
    @ApiOperation(value="根据ID修改菜品上下架", notes="无")
    public ResponseEntity<Void> changeSalable(@PathVariable("id") Long id){
        // TODO 使用redis或者其他方式或者前端进行限流
        foodService.changeSalable(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    @ApiOperation(value="新增菜品", notes="图片需要调用图片上传服务")
    public ResponseEntity<Void> createFood(@Validated Food food){
        if(null == food){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        foodService.createFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @ApiOperation(value="修改菜品", notes="图片需要调用图片上传服务")
    public ResponseEntity<Void> updateFood(@Validated Food food){
        if(null == food){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        foodService.updateFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="删除菜品", notes="图片需要调用图片上传服务")
    public ResponseEntity<Void> deleteFood(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        foodService.deleteFood(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @ApiOperation(value="批量删除菜品", notes="图片需要调用图片上传服务")
    public ResponseEntity<Void> deleteFood(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        foodService.deleteFoods(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
