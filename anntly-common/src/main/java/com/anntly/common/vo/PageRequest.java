package com.anntly.common.vo;

import lombok.Data;

/**
 * @author soledad
 * @Title: PageRequest
 * @ProjectName anntly
 * @Description: 公共请求类
 * @date 2019/1/2521:13
 */
@Data
public class PageRequest {

    // 查询的条件key(Json字符串)
    private String key;

    // 页码
    private Integer page = 1;

    // 一页显示的行数(默认10行)
    private Integer rows = 10;

    // 排序字段
    private String sortBy;

    // 升/降序(默认升序)
    private Boolean desc = false;
}
