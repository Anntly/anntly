package com.anntly.order.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.pojo.UserInfo;
import com.anntly.common.utils.IdWorker;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.order.client.CouponsClient;
import com.anntly.order.client.MenuFoodClient;
import com.anntly.order.config.WebSocket;
import com.anntly.order.dto.Stock;
import com.anntly.order.mapper.OrderMapper;
import com.anntly.order.pojo.Order;
import com.anntly.order.pojo.OrderDetail;
import com.anntly.order.service.OrderDetailService;
import com.anntly.order.service.OrderService;
import com.anntly.order.utils.AnOauth2Utils;
import com.anntly.order.vo.OrderParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author soledad
 * @Title: OrderServiceImpl
 * @ProjectName anntly
 * @Description: OrderServiceImpl
 * @date 2019/3/710:05
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private MenuFoodClient menuFoodClient;

    @Autowired
    private CouponsClient couponsClient;

    @Autowired
    private WebSocket webSocket;

    @Override
    public PageResult<Order> queryPage(PageRequest pageRequest, OrderParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<Order> orders = orderMapper.queryPage(params);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),orders);
    }

    @Override
    @Transactional // TODO 后续加入分布式锁
    public void saveOrder(Order order, HttpServletRequest request) {

        // 当订单详情不存在抛出异常
        if(CollectionUtils.isEmpty(order.getOrderDetails())){

            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }

        // 获取用户信息
        AnOauth2Utils anOauth2Utils = new AnOauth2Utils();
        UserInfo user = anOauth2Utils.getUserJwtFromHeader(request);

        // 判断库存 并 减库存
        List<Stock> stocks = order.getOrderDetails().stream().map(o -> {
            Stock s = new Stock();
            s.setId(o.getFoodId());
            s.setAmount(o.getAmount());
            return s;
        }).collect(Collectors.toList());
        // 减库存
        menuFoodClient.rudeceStock(JsonUtils.serialize(stocks));
        // 生成id
        Long orderId = idWorker.nextId();
        order.setUserId(user.getId());
        order.setUserName(user.getUsername());
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        order.setId(orderId);
        order.setDataStatus(true); // 设置数据状态为true
        order.setStatus(2); // 设置订单状态为 已接单
        order.setType(true); // 设置类型为店内点餐
        // 插入订单详情List
        order.getOrderDetails().stream().forEach(x -> {
            x.setOrderId(orderId);
            x.setDataStatus(true);
        });
        orderDetailService.saveOrderDetailList(order.getOrderDetails());
         // 将未接单状态的 order信息发给商家
        webSocket.sendOneMessage(order.getRestaurantId(), "你有一条新的订单");
        orderMapper.insert(order);
    }

    @Override
    @Transactional
    public BigDecimal settlement(Long id) {
        BigDecimal realPay = BigDecimal.ZERO;
        BigDecimal needPay = BigDecimal.ZERO;

        // 查询订单
        Order order = orderMapper.selectByPrimaryKey(id);
        // 查询订单详情
        List<OrderDetail> orderDetails = orderDetailService.queryOrderDetailsByOrderId(order.getId());
        // 计算需要支付的价格
        for (OrderDetail orderDetail : orderDetails) {
            // add方法返回的是 a+b的值 而不是修改a的值

            needPay = needPay.add(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getAmount().toString())));
            realPay = realPay.add(orderDetail.getSalePrice().multiply(new BigDecimal(orderDetail.getAmount().toString())));
        }
        if(!StringUtils.isEmpty(order.getCouponsId())) {
            // 查询并使用优惠券
            Coupons coupons = couponsClient.queryCouponsById(order.getCouponsId());
            // 当优惠券存在
            if (coupons != null && coupons.getDataStatus()) {
                // 是否满足最低消费
                if (coupons.getNeedCost().compareTo(realPay) <= 0) {
                    // 满减
                    if (coupons.getType()) {
                        realPay = realPay.subtract(coupons.getAmount());
                        // 折扣
                    } else {
                        realPay = realPay.multiply(new BigDecimal(coupons.getDiscount()).divide(new BigDecimal(100)));
                    }
                }
            }
        }
        // 更新订单
        order.setNeedPay(needPay);
        order.setRealPay(realPay);
        orderMapper.updateByPrimaryKeySelective(order);
        return realPay;
    }

    @Override
    @Transactional
    public void hasPay(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setPayStatus(true);
        order.setStatus(5);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    @Transactional
    public void updateOrder(Order order) {
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setDataStatus(false);
        List<OrderDetail> orderDetails = orderDetailService.queryOrderDetailsByOrderId(id);
        List<Long> ids = orderDetails.stream().map(OrderDetail::getId).collect(Collectors.toList());
        orderDetailService.deleteOrderDetails(ids);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    @Transactional
    public void deleteOrders(List<Long> ids) {
        // TODO 同时删除订单详情信息
        orderMapper.updateBatch(ids);
    }


}