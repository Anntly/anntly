package com.anntly.coupons.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: CouponsParams
 * @ProjectName anntly
 * @Description: CouponsParams
 * @date 2019/3/321:56
 */
@Data
public class CouponsParams {

    private String id;

    private String name;

    private Long restaurantId;

    private BigDecimal needCost;

    private Date beginTime;

    private Date endTime;

    private String sname;

    private String sord;
}
