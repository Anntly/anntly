package com.anntly.dish.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author soledad
 * @Title: FoodDto
 * @ProjectName anntly
 * @Description: 用于添加Food
 * @date 2019/1/2217:16
 */
@Data
public class FoodDto {

    private Long id;

    @NotBlank
    private String name;

    @Min(value = 0,message = "价格不能低于0")
    private BigDecimal price;

    private String pic;

    @NotNull(message = "分类不能为空")
    private List<Long> cid;
}
