package com.anntly.shop.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soledad
 * @Title: Desk
 * @ProjectName anntly
 * @Description: 桌子实体类
 * @date 2019/1/2417:15
 */
@Data
@Table(name = "tb_desk")
public class Desk {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long restaurantId;

    private Long roomId;

    private Long menuId;

    private Integer capacity;

    private String employeeCode;

    private String employeeName;

    // 餐桌状态
    private Integer status;

    private Date createTime;

    private Date updateTime;
}
