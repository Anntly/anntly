package com.anntly.shop.vo;

import lombok.Data;

/**
 * @author soledad
 * @Title: MenuParams
 * @ProjectName anntly
 * @Description: 菜单查询类
 * @date 2019/2/410:32
 */
@Data
public class MenuParams {

    private Long id;

    private Long restaurantId;

    private String name;

    private Integer status;

    private String sname;

    private String sord;
}
