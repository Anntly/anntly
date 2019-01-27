package com.anntly.shop.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soledad
 * @Title: FooEvaluate
 * @ProjectName anntly
 * @Description: 菜单菜品评价类
 * @date 2019/1/2421:30
 */
@Data
@Table(name = "tb_food_evaluate")
public class FooEvaluate {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long restaurantId;

    private Long mfId;

    private Long userId;

    private Integer star;

    private String evaluate;

    private Boolean dataStatus;

    private Date createTime;

    private Date updateTime;
}
