package com.anntly.shop.dto;

import lombok.Data;

import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @author soledad
 * @Title: OrderFood
 * @ProjectName anntly
 * @Description: 下单时候的Food
 * @date 2019/3/1417:05
 */
@Data
public class OrderFood {

    private Long id;

    private Long mCid;

    private String showName;

    private BigDecimal price;

    private Integer discount;

    private String pic;

    @Transient
    private Integer num = 0;
}
