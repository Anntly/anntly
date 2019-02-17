package com.anntly.shop.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: Room
 * @ProjectName anntly
 * @Description: 包间实体类
 * @date 2019/1/2417:06
 */
@Data
@Table(name = "tb_room")
public class Room {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long restaurantId;

    private Integer type;

    private Integer star;

    private BigDecimal minComsume;

    private Integer capacity;

    private String employeeCode;

    private String employeeName;

    private Integer status;

    @JsonProperty("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonProperty("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
