package com.anntly.order.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.order.pojo.OrderDetail;
import com.anntly.order.service.OrderDetailService;
import com.anntly.order.vo.OrderDetailParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author soledad
 * @Title: OrderDetailController
 * @ProjectName anntly
 * @Description: 订单详情实体类
 * @date 2019/3/715:22
 */
@RestController
@RequestMapping("/detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/page")
    @ApiOperation(value="获取餐厅订单详情列表", notes="与餐厅Id绑定")
    public ResponseEntity<PageResult<OrderDetail>> queryRestaurantPage(PageRequest pageRequest){
        OrderDetailParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), OrderDetailParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(orderDetailService.queryPage(pageRequest,params));
    }

    @PostMapping
    @ApiOperation(value="增添订单详情", notes="与订单Id绑定")
    public ResponseEntity<Void> saveOrderDetail(OrderDetail orderDetail){
        if(null == orderDetail){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        orderDetailService.saveOrderDetail(orderDetail);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @ApiOperation(value="修改订单详情", notes="与订单Id绑定")
    public ResponseEntity<Void> updateOrderDetail(OrderDetail orderDetail){
        if(null == orderDetail){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        orderDetailService.updateOrderDetail(orderDetail);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="删除订单详情", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @ApiOperation(value="批量删除", notes="无")
    public ResponseEntity<Void> deleteOrderDetails(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        orderDetailService.deleteOrderDetails(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value="根据订单Id获取订单详情", notes="无")
    public ResponseEntity<List<OrderDetail>> queryOrderDetailsByOrderId(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(orderDetailService.queryOrderDetailsByOrderId(id));
    }
}
