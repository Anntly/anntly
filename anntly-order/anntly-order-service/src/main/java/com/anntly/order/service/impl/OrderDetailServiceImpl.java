package com.anntly.order.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.order.client.MenuFoodClient;
import com.anntly.order.dto.FoodReport;
import com.anntly.order.dto.FoodResult;
import com.anntly.order.mapper.OrderDetailMapper;
import com.anntly.order.pojo.OrderDetail;
import com.anntly.order.service.OrderDetailService;
import com.anntly.order.vo.OrderDetailParams;
import com.anntly.shop.dto.FoodDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author soledad
 * @Title: OrderDetailServiceImpl
 * @ProjectName anntly
 * @Description: OrderDetailServiceImpl
 * @date 2019/3/715:38
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private MenuFoodClient menuFoodClient;

    @Override
    public PageResult<OrderDetail> queryPage(PageRequest pageRequest, OrderDetailParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<OrderDetail> orderDetails = orderDetailMapper.queryPage(params);
        PageInfo<OrderDetail> pageInfo = new PageInfo<>(orderDetails);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),orderDetails);
    }

    @Override
    @Transactional
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetail.setDataStatus(true);
        // 根据foodId查询价格并设置价格
        List<FoodDto> foodDtos = menuFoodClient.queryFoodsByIds(Arrays.asList(orderDetail.getFoodId()));
        FoodDto foodDto = foodDtos.get(0);
        orderDetail.setName(foodDto.getShowName());
        orderDetail.setPrice(foodDto.getPrice());
        orderDetail.setDiscount(foodDto.getDiscount());
        orderDetail.setSalePrice(foodDto.getPrice().multiply(new BigDecimal(foodDto.getDiscount()).divide(new BigDecimal(100))));
        orderDetailMapper.insert(orderDetail);
    }

    @Override
    @Transactional
    public void saveOrderDetailList(List<OrderDetail> orderDetails) {
        // 批量插入订单详情
        List<Long> ids = orderDetails.stream().map(OrderDetail::getFoodId).collect(Collectors.toList());
        List<FoodDto> foodDtos = menuFoodClient.queryFoodsByIds(ids);
        for (FoodDto foodDto : foodDtos) {
            for (OrderDetail orderDetail : orderDetails) {
                if(foodDto.getId().equals(orderDetail.getFoodId())){
                    orderDetail.setName(foodDto.getShowName());
                    orderDetail.setPrice(foodDto.getPrice());
                    orderDetail.setDiscount(foodDto.getDiscount());
                    orderDetail.setSalePrice(foodDto.getPrice().multiply(new BigDecimal(foodDto.getDiscount()).divide(new BigDecimal(100))));
                }
            }
        }
        orderDetailMapper.insertList(orderDetails);
    }

    @Override
    @Transactional
    public void updateOrderDetail(OrderDetail orderDetail) {
        orderDetailMapper.updateByPrimaryKeySelective(orderDetail);
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(id);
        orderDetail.setDataStatus(false);
        orderDetailMapper.updateByPrimaryKeySelective(orderDetail);
    }

    @Override
    @Transactional
    public void deleteOrderDetails(List<Long> ids) {
        orderDetailMapper.updateBatch(ids);
    }

    @Override
    public List<OrderDetail> queryOrderDetailsByOrderId(Long id) {
        return orderDetailMapper.queryOrderDetailsByOrderId(id);
    }

    @Override
    public FoodResult queryFoodReport(Long restaurantId) {
        List<FoodReport> foodReports = orderDetailMapper.queryFoodReport(restaurantId);
        if(CollectionUtils.isEmpty(foodReports)){
            throw new AnnException(ExceptionEnum.FOODS_NOT_FOUND);
        }
        List<String> name = foodReports.stream().map(FoodReport::getName).collect(Collectors.toList());
        List<Integer> num = foodReports.stream().map(FoodReport::getNum).collect(Collectors.toList());
        FoodResult foodResult = new FoodResult();
        foodResult.setName(name);
        foodResult.setNum(num);
        return foodResult;
    }


}
