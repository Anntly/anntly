package com.anntly.shop.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: MenuFood
 * @ProjectName MenuFood实体类
 * @Description: MenuFood实体类
 * @date 2019/1/2420:11
 */
@Data
@Table(name = "tb_menu_food")
public class MenuFood {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long menuId;

    private Long foodId;

    private Long mCid;

    @Transient
    private String categoryName;

    private Long restaurantId;

    private String showName;

    private String description;

    private BigDecimal price;

    private Integer discount;

    private String pic;

    private Integer star;

    private Integer stock;

    private Integer monthAmount;

    private Boolean dataStatus;

    private Boolean status;

    private Boolean recommend;

    @Column(name = "create_time")
    @JsonProperty("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name = "update_time")
    @JsonProperty("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
