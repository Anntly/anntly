package com.anntly.shop.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    private String name;

    private Long restaurantId;

    @Transient
    private String restaurantName;

    private String description;

    private Integer status;

    private Boolean dataStatus;

    @JsonProperty("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonProperty("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
