package com.anntly.dish.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 功能描述: 菜品(概要)
 *
 * @param:
 * @return:
 * @auther: Soledad
 * @date: 2019/1/11 0011 16:44
 */
@Data
@Table(name = "tb_food")
public class Food {

    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;


    private String name;

    private BigDecimal price;

    private String pic;

    // [1,2,3,4] 存入分类Json数据
    private Long cid;

    private Boolean saleable;

    private Boolean dataStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    @JsonProperty("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
