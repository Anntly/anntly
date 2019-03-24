package com.anntly.coupons.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author soledad
 * @Title: CouponsDto
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/2416:10
 */
@Data
public class CouponsDto {

    // 优惠券id
    private String id;

    private Long available = 1L;

    // 优惠券名称
    private String name;

    // 满减条件
    private String condition;

    // 开始时间(时间戳)
    private Long startAt;

    // 失效日期
    private Long endAt;

    // 描述信息
    private String description;

    // 不可用原因
    private String reason;

    // 折扣优惠金额(单位分)
    private BigDecimal value;

    // 折扣券优惠金额文案
    private String valueDesc;

    // 单位文案
    private String unitDesc;
}
