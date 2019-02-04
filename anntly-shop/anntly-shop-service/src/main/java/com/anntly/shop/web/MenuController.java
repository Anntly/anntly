package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.pojo.Menu;
import com.anntly.shop.service.MenuService;
import com.anntly.shop.vo.MenuParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            params.setOrderVal(pageRequest.getSortBy()+(pageRequest.getDesc()?" desc":" asc"));
        }
        return ResponseEntity.ok(menuService.queryPage(pageRequest,params));
    }

    @PostMapping
    @ApiOperation(value="增添餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> saveRestaurant(Menu menu){
        // TODO 需要与餐厅Id相绑定
        if(null == menu){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuService.saveMenu(menu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @ApiOperation(value="修改餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> updateRestaurant(Menu menu){
        // TODO 需要与餐厅Id相绑定
        if(null == menu){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        menuService.updateMenu(menu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 删除需要与菜单菜品信息连表删除
}
