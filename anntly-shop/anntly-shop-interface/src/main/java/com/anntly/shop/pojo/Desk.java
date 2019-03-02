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

    private String name;

    private Long restaurantId;

    private Long roomId;

    private Long menuId;

    @Transient
    private String menuName;

    private Integer capacity;

    // 餐桌状态
    private Boolean status;

    private String qrCode; //二维码图片

    @JsonProperty("create_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    @JsonProperty("update_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
}
