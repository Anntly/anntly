package com.anntly.shop.vo;

import lombok.Data;

/**
 * @author soledad
 * @Title: DeskParams
 * @ProjectName anntly
 * @Description: DeskParams
 * @date 2019/3/121:51
 */
@Data
public class DeskParams {

    private Long id;

    private String name;

    private Long roomId;

    private Long menuId;

    private String sname;

    private String sord;
}
