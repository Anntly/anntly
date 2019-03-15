package com.anntly.order.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: Order
 * @ProjectName anntly
 * @Description: 订单实体类
 * @date 2019/3/520:25
 */
@Data
@Table(name = "tb_order")
public class Order {

    @Id
    private Long id;

    // false 外卖 true 店内点餐
    private Boolean type;

    private Long restaurantId;

    private String restaurantName;

    private Long userId;

    private String userName;

    // 备注
    private String note;

    // 收货地址
    private String address;

    // 配送员Id
    private Long courierId;

    private String courierName;

    private Long deskId;

    private String deskName;

    private String couponsId;

    private String couponsName;

    private BigDecimal needPay;

    private BigDecimal realPay;

    // 支付状态 false 未支付 true已付款
    private Boolean payStatus;

    // 订单状态 1 未接单 2 已接单 3 配送中  4 已取消  5 已完成
    private Integer status;

    // 支付类型 1 支付宝 2 微信 3 现金 4 其他
    private Integer payType;

    // 餐盒费
    private BigDecimal boxFee;

    // 配送费
    private BigDecimal shippingFee;

    // 配送方式 1 商家配送 2 外卖员配送
    private Integer deliveryMode;

    // 菜品送达时间
    private Date deliveryTime;

    private Boolean dataStatus;

    // 创建时间
    @JsonProperty("create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date createTime;

    // 创建时间
    @JsonProperty("update_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date updateTime;

    @Transient
    private List<OrderDetail> orderDetails;
}
