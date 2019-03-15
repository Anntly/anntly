package com.anntly.coupons.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: Coupons
 * @ProjectName anntly
 * @Description: 优惠券实体类
 * @date 2019/3/320:48
 */
@Data
@Table(name = "tb_coupons")
public class Coupons {

    @Id
    // id由后端生成，防止被猜测
    private String id;

    private String name;

    // 发放的餐厅
    private Long restaurantId;

    // 优惠券类型(false 折扣 true 减免)
    private Boolean type;

    // 需要满足的金额
    private BigDecimal needCost;

    private String description;

    // 减免金额
    private BigDecimal amount;

    // 折扣
    private Integer discount;

    // 发放数量
    private Integer num;

    // 优惠券状态 true 有效 false 过期
    private Boolean status;

    // 数据状态
    private Boolean dataStatus;

    // 创建时间
    @JsonProperty("create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date createTime;

    // 优惠券生效时间
    @JsonProperty("begin_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date beginTime;

    // 优惠券失效时间
    @JsonProperty("end_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime;

}
