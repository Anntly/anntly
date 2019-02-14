package com.anntly.shop.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author soledad
 * @Title: MenuFoodParams
 * @ProjectName anntly
 * @Description: MenuFoodParams
 * @date 2019/2/1214:12
 */
@Data
public class MenuFoodParams {

    private Long id;

    private Long menuId;

    private String showName;

    private BigDecimal startPrice = new BigDecimal(0);

    private BigDecimal endPrice;

    private String sname;

    private String sord;
}
