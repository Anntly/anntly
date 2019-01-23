package com.anntly.dish.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author soledad
 * @Title: CategoryOptions
 * @ProjectName anntly
 * @Description: 用于级联选择的分类信息
 * @date 2019/1/2212:30
 */
@Data
public class CategoryOptions {

    @JsonProperty("value")
    private Long id;

    @JsonProperty("label")
    private String name;

    @JsonIgnore
    private Boolean isParent;

    private List<CategoryOptions> children;
}
