package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.Desk;
import com.anntly.shop.service.DeskService;
import com.anntly.shop.vo.DeskParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author soledad
 * @Title: DeskController
 * @ProjectName anntly
 * @Description: 餐桌管理
 * @date 2019/3/121:46
 */
@RestController
@RequestMapping("/desk")
public class DeskController {

    @Autowired
    private DeskService deskService;

    @GetMapping("/page")
    @ApiOperation(value="获取餐桌菜单列表", notes="与房间Id绑定")
    public ResponseEntity<PageResult<Desk>> queryMenuPage(PageRequest pageRequest){
        DeskParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), DeskParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname("d." + pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(deskService.queryPage(pageRequest,params));
    }

    @PostMapping
    @ApiOperation(value="增添餐厅餐桌列表", notes="与房间Id绑定")
    public ResponseEntity<Void> saveRestaurant(Desk desk){
        if(null == desk){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        deskService.saveDesk(desk);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @ApiOperation(value="修改餐厅餐桌列表", notes="与房间Id绑定")
    public ResponseEntity<Void> updateRestaurant(Desk desk){
        if(null == desk){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        deskService.updateDesk(desk);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="删除单个餐桌", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteMenuCat(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        deskService.deleteDesk(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @ApiOperation(value="批量删除菜品分类", notes="无")
    public ResponseEntity<Void> deleteMenuCat(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        deskService.deleteDesks(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/nodes")
    @ApiOperation(value="根据餐厅Id查询所有desk节点", notes="无")
    public ResponseEntity<List<Node>> queryDeskNodesByRid(@RequestParam("restaurantId") Long restaurantId){
        if(null == restaurantId){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(deskService.queryDeskNodesByRid(restaurantId));
    }
}
