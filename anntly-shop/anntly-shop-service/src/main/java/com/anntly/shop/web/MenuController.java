package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.Menu;
import com.anntly.shop.service.MenuService;
import com.anntly.shop.vo.MenuParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author soledad
 * @Title: MenuController
 * @ProjectName anntly
 * @Description: 菜单Controller
 * @date 2019/1/2420:38
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('QUERY_MENU')")
    @ApiOperation(value="获取餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<PageResult<Menu>> queryMenuPage(PageRequest pageRequest){
        MenuParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), MenuParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(menuService.queryPage(pageRequest,params));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_MENU')")
    @ApiOperation(value="增添餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> saveMenu(Menu menu){
        // TODO 需要与餐厅Id相绑定
        if(null == menu){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuService.saveMenu(menu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_MENU')")
    @ApiOperation(value="修改餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> updateMenu(Menu menu){
        // TODO 需要与餐厅Id相绑定
        if(null == menu){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuService.updateMenu(menu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 删除需要与菜单菜品信息连表删除
    // 删除分类会联动删除该分类下的所有菜品
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_MENU')")
    @ApiOperation(value="删除单个菜品分类", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteMenu(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuService.deleteMenu(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @PreAuthorize("hasAuthority('DELETE_MENU')")
    @ApiOperation(value="批量删除菜品分类", notes="无")
    public ResponseEntity<Void> deleteMenu(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuService.deleteMenus(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/re")
    @ApiOperation(value="根据餐厅Id查询菜单Nodes", notes="无")
    public ResponseEntity<List<Node>> queryNodesByRid(@RequestParam("restaurantId") Long restaurantId){
        if(null == restaurantId){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(menuService.queryNodesByRid(restaurantId));
    }
}
