package com.anntly.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: OrderParams
 * @ProjectName anntly
 * @Description: OrderParams
 * @date 2019/3/611:59
 */
@Data
public class OrderParams {


    private Long id;

    private Long restaurantId;

    // false 外卖 true 店内点餐
    private Boolean type;

    // 大于等于
    private BigDecimal realPay;

    // 支付状态
    private Boolean payStatus;

    // 订单状态
    private Integer status;

    private Date beginTime;

    private Date endTime;

    private String sname;

    private String sord;
}
