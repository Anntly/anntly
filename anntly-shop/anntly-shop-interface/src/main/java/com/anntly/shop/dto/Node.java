package com.anntly.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author soledad
 * @Title: Node
 * @ProjectName anntly
 * @Description: Node
 * @date 2019/2/1316:54
 */
@Data
public class Node {

    @JsonProperty("value")
    private Long id;

    @JsonProperty("label")
    private String name;
}
