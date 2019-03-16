package com.anntly.order.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.order.dto.FoodResult;
import com.anntly.order.pojo.OrderDetail;
import com.anntly.order.vo.OrderDetailParams;

import java.util.List;

/**
 * @author soledad
 * @Title: OrderDetailService
 * @ProjectName anntly
 * @Description: OrderDetailService
 * @date 2019/3/715:37
 */
public interface OrderDetailService {
    PageResult<OrderDetail> queryPage(PageRequest pageRequest, OrderDetailParams params);

    void saveOrderDetail(OrderDetail orderDetail);

    void saveOrderDetailList(List<OrderDetail> orderDetails);

    void updateOrderDetail(OrderDetail orderDetail);

    void deleteOrderDetail(Long id);

    void deleteOrderDetails(List<Long> ids);

    List<OrderDetail> queryOrderDetailsByOrderId(Long id);

    FoodResult queryFoodReport(Long restaurantId);
}
