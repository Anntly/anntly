package com.anntly.dish.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author soledad
 * @Title: FoodKey
 * @ProjectName anntly
 * @Description: 用于查询Food
 * @date 2019/1/1616:16
 */
@Data
public class FoodKey {
    private Long id;
    private String name;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
}
