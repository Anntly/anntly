package com.anntly.shop.vo;


import lombok.Data;

/**
 * @author soledad
 * @Title: EmployeeParams
 * @ProjectName anntly
 * @Description: EmployeeParams
 * @date 2019/2/2714:03
 */
@Data
public class EmployeeParams {

    private Long id; // 员工编号

    private Long restaurantId;

    private String name;

    private String phone;

    // 员工状态
    private Integer status;

    private String sname;

    private String sord;
}
