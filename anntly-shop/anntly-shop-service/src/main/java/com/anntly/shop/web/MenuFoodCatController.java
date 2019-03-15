package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.MenuCategory;
import com.anntly.shop.service.MenuCatService;
import com.anntly.shop.vo.MenuCatParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author soledad
 * @Title: MenuFoodCatController
 * @ProjectName anntly
 * @Description: 菜单菜品分类管理
 * @date 2019/2/411:17
 */
@RestController
@RequestMapping("/cat")
public class MenuFoodCatController {

    @Autowired
    private MenuCatService menuCatService;

    @GetMapping("/page")
    @ApiOperation(value="获取餐厅菜单分类列表", notes="与菜单Id绑定")
    public ResponseEntity<PageResult<MenuCategory>> queryMenuPage(PageRequest pageRequest){
        MenuCatParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), MenuCatParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(menuCatService.queryPage(pageRequest,params));
    }

    @GetMapping("/nodes/{menuId}")
    @ApiOperation(value="根据菜单Id获取菜单菜品分类节点", notes="与菜单Id绑定")
    public ResponseEntity<List<Node>> queryCatsByMenuId(@PathVariable("menuId") Long menuId){
        if(null == menuId){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(menuCatService.queryCatsByMenuId(menuId));
    }

    @PostMapping
    @ApiOperation(value="增添餐厅菜单分类列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> saveRestaurant(MenuCategory menuCategory){
        if(null == menuCategory){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuCatService.saveMenuCategory(menuCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @ApiOperation(value="修改餐厅菜单分类列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> updateRestaurant(MenuCategory menuCategory){
        if(null == menuCategory){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuCatService.updateMenuCategory(menuCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 删除分类会联动删除该分类下的所有菜品
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除单个菜品分类", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteMenuCat(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuCatService.deleteMenuCat(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @ApiOperation(value="批量删除菜品分类", notes="无")
    public ResponseEntity<Void> deleteMenuCat(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuCatService.deleteMenuCats(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
