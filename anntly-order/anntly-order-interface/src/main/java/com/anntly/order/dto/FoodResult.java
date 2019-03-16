package com.anntly.order.dto;

import lombok.Data;

import java.util.List;

/**
 * @author soledad
 * @Title: FoodResult
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1614:34
 */
@Data
public class FoodResult {

    private List<String> name;

    private List<Integer> num;
}
