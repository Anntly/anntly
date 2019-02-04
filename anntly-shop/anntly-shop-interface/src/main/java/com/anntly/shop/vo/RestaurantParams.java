package com.anntly.shop.vo;

import lombok.Data;

/**
 * @author soledad
 * @Title: RestaurantParams
 * @ProjectName anntly
 * @Description: 查询入参实体类
 * @date 2019/1/2910:23
 */
@Data
public class RestaurantParams {

    private Long id;

    private String name;

    private Integer aid;

    private String phone;

    private Integer status;

    private String orderVal;
}
