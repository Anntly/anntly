package com.anntly.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author soledad
 * @Title: OrderDetailParams
 * @ProjectName anntly
 * @Description: OrderDetailParams
 * @date 2019/3/715:42
 */
@Data
public class OrderDetailParams {


    private Long id;

    private Long orderId;

    // 售价
    private BigDecimal salePrice;

    // 菜品名称
    private String name;

    private String sname;

    private String sord;
}
