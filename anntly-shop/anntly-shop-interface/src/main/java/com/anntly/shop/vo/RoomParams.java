package com.anntly.shop.vo;

import lombok.Data;

/**
 * @author soledad
 * @Title: RoomParams
 * @ProjectName anntly
 * @Description: RoomParams
 * @date 2019/3/116:17
 */
@Data
public class RoomParams {

    private Long id;

    private Long restaurantId;

    private String name;

    private String employeeId;

    private String sname;

    private String sord;
}
