package com.anntly.shop.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: Restaurant
 * @ProjectName anntly
 * @Description: 餐厅实体类
 * @date 2019/1/2416:26
 */
@Data
@Table(name = "tb_restaurant")
public class Restaurant {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private String description;

    private Long userId;

    private Integer pid;

    private Integer cid;

    private Integer aid;

    private String address;

    private BigDecimal lon;

    private BigDecimal lat;

    private String phone;

    private String pic;

    private String photo;

    private String mainFoods;

    private Integer star;

    private BigDecimal avg;

    private Date beginTime;

    private Date endTime;

    private String license;

    // 餐厅状态
    private Integer status;

    private BigDecimal deliveryArea;

    private Date createTime;

    private Date updateTime;
}
