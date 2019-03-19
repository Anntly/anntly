package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.pojo.Room;
import com.anntly.shop.service.RoomService;
import com.anntly.shop.vo.RoomParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author soledad
 * @Title: RoomController
 * @ProjectName anntly
 * @Description: 包间管理Controller
 * @date 2019/2/2821:19
 */
@RestController
@RequestMapping("/room")
@Api(value = "room-controller",description = "员包间controller")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('QUERY_SHOP')")
    @ApiOperation(value="获取房间列表", notes="需要传入餐厅Id")
    public ResponseEntity<PageResult<Room>> queryRestaurantPage(PageRequest pageRequest){
        // 需要与 餐厅绑定
        RoomParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), RoomParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname("r."+pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(roomService.queryPage(pageRequest,params));
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADD_SHOP')")
    @ApiOperation(value="增添餐厅房间列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> saveRestaurant(Room room){
        if(null == room){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        roomService.saveRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_SHOP')")
    @ApiOperation(value="修改餐厅菜单列表", notes="与餐厅Id绑定")
    public ResponseEntity<Void> updateRestaurant(Room room){
        if(null == room){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        roomService.updateRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REMOVE_SHOP')")
    @ApiOperation(value="删除单个房间", notes="房间中的餐桌一起删除")
    public ResponseEntity<Void> deleteMenuCat(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        roomService.deleteRoom(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @PreAuthorize("hasAuthority('REMOVE_SHOP')")
    @ApiOperation(value="批量删除菜品分类", notes="无")
    public ResponseEntity<Void> deleteMenuCat(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        roomService.deleteRooms(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
