package com.anntly.shop.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: MenuFood
 * @ProjectName anntly
 * @Description: TODO
 * @date 2019/1/2420:11
 */
@Data
@Table(name = "tb_menu_food")
public class MenuFood {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long foodId;

    private Long mCid;

    private String showName;

    private String description;

    private BigDecimal price;

    private Integer discount;

    private String pic;

    private Integer star;

    private Integer stock;

    private Integer monthAmount;

    private Boolean dataStatus;

    private Boolean recommend;

    private Date createTime;

    private Date updateTime;
}
