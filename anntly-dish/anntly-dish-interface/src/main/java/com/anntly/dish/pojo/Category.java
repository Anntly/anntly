package com.anntly.dish.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Table(name = "tb_food_category")
public class Category {

    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;

    private Long pid;

    @NotBlank
    private String name;

    private Integer sort;

    private Boolean isParent;

    @JsonIgnore
    private Boolean dataStatus;

    @Transient
    /**
     * 封装子分类，不与数据库对应
     */
    private List<Category> children;
}
