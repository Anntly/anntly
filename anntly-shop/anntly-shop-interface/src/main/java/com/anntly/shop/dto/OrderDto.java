package com.anntly.shop.dto;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author soledad
 * @Title: OrderDto
 * @ProjectName anntly
 * @Description: 下单显示的Dto
 * @date 2019/3/1417:03
 */
@Data
public class OrderDto {

    private Long id;

    private String name;

    @Transient
    private List<OrderFood> orderFoods;
}
