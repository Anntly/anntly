package com.anntly.common.vo;

import lombok.Data;

import java.util.List;

/**
 *
 * 功能描述: 数据表格返回封装类
 *
 * @param:
 * @return:
 * @auther: Soledad
 * @date: 2018/11/10 0010 14:49
 */
@Data
public class PageResult<T> {

    private Long total;// 总条数
    private Long totalPage;// 总页数
    private List<T> items;// 当前页数据,为了通用使用范式

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
