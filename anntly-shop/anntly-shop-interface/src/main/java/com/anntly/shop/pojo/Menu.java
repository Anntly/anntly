package com.anntly.shop.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soledad
 * @Title: Menu
 * @ProjectName anntly
 * @Description: 菜单类
 * @date 2019/1/2419:58
 */
@Data
@Table(name = "tb_menu")
public class Menu {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long restaurantId;

    private String description;

    private Integer status;

    private Boolean dataStatus;

    private Date createTime;

    private Date updateTime;
}
