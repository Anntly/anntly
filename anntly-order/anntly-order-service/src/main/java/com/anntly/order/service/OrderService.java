package com.anntly.order.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.order.dto.PayTypeReport;
import com.anntly.order.pojo.Order;
import com.anntly.order.vo.OrderParams;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author soledad
 * @Title: OrderService
 * @ProjectName anntly
 * @Description: OrderService
 * @date 2019/3/710:05
 */
public interface OrderService {
    PageResult<Order> queryPage(PageRequest pageRequest, OrderParams params);

    void saveOrder(Order order, HttpServletRequest request);

    void updateOrder(Order order);

    void deleteOrder(Long id);

    void deleteOrders(List<Long> ids);

    BigDecimal settlement(Long id);

    void hasPay(Long id);

    BigDecimal[] queryReportData(Long restaurantId);

    BigDecimal[] queryReportExpend(Long restaurantId);

    List<PayTypeReport> queryReportPayType(Long restaurantId);
}
