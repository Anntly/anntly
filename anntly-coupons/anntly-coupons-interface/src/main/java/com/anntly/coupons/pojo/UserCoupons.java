package com.anntly.coupons.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soledad
 * @Title: UserCoupons
 * @ProjectName anntly
 * @Description: 用户认领优惠券表
 * @date 2019/3/321:03
 */
@Data
@Table(name = "tb_user_coupons")
public class UserCoupons {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long userId;

    private String couponsId;

    private Long restaurantId;

    // 优惠券状态 1已使用 2未使用 3已过期
    private Integer couponsStatus;

    @JsonProperty("create_time")
    private Date createTime;

    @JsonProperty("end_time")
    private Date endTime;
}
