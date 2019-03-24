package com.anntly.order.dto;

import lombok.Data;

/**
 * @author soledad
 * @Title: OrderDto
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1512:51
 */
@Data
public class OrderDto {

    private Long restaurantId;

    private String restaurantName;

    private String note;

    private Long deskId;

    private String deskName;

    // 优惠券Id
    private String couponsId;

    private String orderDetails;
}
