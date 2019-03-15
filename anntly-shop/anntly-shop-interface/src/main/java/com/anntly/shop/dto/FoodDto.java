package com.anntly.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author soledad
 * @Title: FoodDto
 * @ProjectName anntly
 * @Description: FoodDto
 * @date 2019/3/717:45
 */
@Data
public class FoodDto {

    private Long id;

    private String showName;

    private BigDecimal price;

    private Integer discount;
}
