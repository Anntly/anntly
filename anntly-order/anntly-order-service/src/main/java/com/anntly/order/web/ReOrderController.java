package com.anntly.order.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.order.dto.OrderDto;
import com.anntly.order.pojo.Order;
import com.anntly.order.pojo.OrderDetail;
import com.anntly.order.service.OrderService;
import com.anntly.order.vo.OrderParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author soledad
 * @Title: ReOrderController
 * @ProjectName anntly
 * @Description: 餐厅订单Controller
 * @date 2019/3/611:58
 */
@RestController
@RequestMapping("/restaurant")
public class ReOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    @ApiOperation(value="获取餐厅订单列表", notes="与餐厅Id绑定")
    public ResponseEntity<PageResult<Order>> queryOrderPage(PageRequest pageRequest){
        OrderParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), OrderParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(orderService.queryPage(pageRequest,params));
    }

    @PostMapping
    @ApiOperation(value="餐厅增添订单，无需用户登录", notes="与餐厅Id绑定")
    public ResponseEntity<Void> saveOrder(HttpServletRequest request,@RequestBody OrderDto orderDto){
        if(null == orderDto){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        List<OrderDetail> orderDetails = JsonUtils.parseList(orderDto.getOrderDetails(), OrderDetail.class);
        Order order = new Order();
        order.setRestaurantId(orderDto.getRestaurantId());
        order.setRestaurantName(orderDto.getRestaurantName());
        order.setDeskId(orderDto.getDeskId());
        order.setDeskName(orderDto.getDeskName());
        order.setNote(orderDto.getNote());
        order.setOrderDetails(orderDetails);
        orderService.saveOrder(order,request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @ApiOperation(value="修改订单", notes="与餐厅Id绑定")
    public ResponseEntity<Void> updateOrder(Order order){
        if(null == order){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        orderService.updateOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/settlement")
    @ApiOperation(value="结算订单", notes="与座号Id绑定")
    public ResponseEntity<BigDecimal> settlement( Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(orderService.settlement(id));
    }

    @PutMapping("/haspay")
    @ApiOperation(value="修改订单状态为已付款", notes="与订单Id绑定")
    public ResponseEntity<Void> hasPay(Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        orderService.hasPay(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 删除需要与菜单菜品信息连表删除
    // 删除分类会联动删除该分类下的所有菜品
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除订单", notes="命名需要与数据库对应")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/ids")
    @ApiOperation(value="批量删除菜品分类", notes="无")
    public ResponseEntity<Void> deleteOrders(@RequestParam("ids") List<Long> ids){
        if(null == ids){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        orderService.deleteOrders(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}