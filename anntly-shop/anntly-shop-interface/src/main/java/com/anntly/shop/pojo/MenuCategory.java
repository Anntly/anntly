package com.anntly.shop.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soledad
 * @Title: MenuCategory
 * @ProjectName anntly
 * @Description: TODO
 * @date 2019/1/2420:18
 */
@Data
@Table(name = "tb_menu_food_cat")
public class MenuCategory {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long menuId;

    private String name;

    private Boolean dataStatus;

    private Date createTime;

    private Date updateTime;
}
