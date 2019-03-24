package com.anntly.order.dto;

import com.anntly.order.pojo.OrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: UserOrderDto
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/2111:19
 */
@Data
public class UserOrderDto {

    private Long id;

    // false 外卖 true 店内点餐
    private Boolean type;

    private Long restaurantId;

    private String restaurantName;

    private String note;

    private String address;

    // 配送方式 1 商家配送 2 外卖员配送
    private Integer deliveryMode;

    // 餐盒费
    private BigDecimal boxFee;

    // 配送费
    private BigDecimal shippingFee;

    // 桌号
    private Long deskId;

    private String deskName;

    // 支付类型 1 支付宝 2 微信 3 现金 4 其他
    private Integer payType;

    // 支付状态 false 未支付 true已付款
    private Boolean payStatus;

    // 订单状态 1 未接单 2 已接单 3 配送中  4 已取消  5 已完成
    private Integer status;

    private BigDecimal needPay;

    private BigDecimal realPay;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<OrderDetail> orderDetails;
}
