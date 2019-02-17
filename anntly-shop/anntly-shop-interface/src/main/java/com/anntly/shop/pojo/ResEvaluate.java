package com.anntly.shop.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soledad
 * @Title: ResEvaluate
 * @ProjectName anntly
 * @Description: 餐厅评价实体类
 * @date 2019/1/2419:34
 */
@Data
@Table(name = "tb_restaurant_evaluate")
public class ResEvaluate {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long restaurantId;

    private Long userId;

    private Long orderId;

    private Integer star;

    private String foods;

    private String evaluate;

    private Boolean dataStatus;

    private Date createTime;

    private Date updateTime;
}
