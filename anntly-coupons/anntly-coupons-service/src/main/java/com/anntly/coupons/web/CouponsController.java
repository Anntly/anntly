package com.anntly.coupons.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.service.CouponsService;
import com.anntly.coupons.vo.CouponsParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author soledad
 * @Title: CouponsController
 * @ProjectName anntly
 * @Description: 优惠券Controller
 * @date 2019/3/321:27
 */
@RestController
@Api(value = "coupons-controller",description = "优惠券接口")
public class CouponsController {

    @Autowired
    private CouponsService couponsService;

    @GetMapping("/restaurant/page")
    @ApiOperation(value="获取餐厅优惠券列表", notes="命名需要与数据库对应")
    public ResponseEntity<PageResult<Coupons>> queryRestaurantPage(PageRequest pageRequest){
        CouponsParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), CouponsParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(couponsService.queryPage(pageRequest,params));
    }

    @PostMapping("/restaurant")
    @ApiOperation(value="增添优惠券", notes="与餐厅Id绑定")
    public ResponseEntity<Void> saveRestaurant(Coupons coupons){
        if(null == coupons){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        couponsService.saveCoupons(coupons);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/restaurant")
    @ApiOperation(value="修改优惠券", notes="与餐厅Id绑定")
    public ResponseEntity<Void> updateRestaurant(Coupons coupons){
        // TODO 需要与餐厅Id相绑定
        if(null == coupons){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        couponsService.updateCoupons(coupons);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 删除需要与菜单菜品信息连表删除
    // 删除分类会联动删除该分类下的所有菜品
    @DeleteMapping("/restaurant/{id}")
    @ApiOperation(value="删除单个菜品分类", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteMenuCat(@PathVariable("id") String id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        couponsService.deleteCoupons(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/restaurant/ids")
    @ApiOperation(value="批量删除菜品分类", notes="无")
    public ResponseEntity<Void> deleteMenuCat(@RequestParam("ids") List<String> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        couponsService.deleteCouponsList(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/restaurant/status/{id}")
    @ApiOperation(value="根据ID修改优惠券状态", notes="无")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") String id){
        // TODO 使用redis或者其他方式或者前端进行限流
        couponsService.changeStatus(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
