package com.anntly.shop.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
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

    @Column(name = "`name`")
    private String name;

    private String description;

    private Long userId;

    private Integer pid;

    private Integer cid;

    private Integer aid;

    private Integer nid;

    private String address;

    private BigDecimal lon;

    private BigDecimal lat;

    private String phone;

    private String pic;

    private String photo;

    private String mainFoods;

    private Integer star;

    @Column(name = "`avg`")
    private BigDecimal avg;

    @DateTimeFormat(pattern="HH:mm")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm")
    private Date beginTime;

    @DateTimeFormat(pattern="HH:mm")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm")
    private Date endTime;

    private String license;

    // 餐厅状态
    private Integer status;

    private BigDecimal deliveryArea;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Boolean dataStatus;
}
