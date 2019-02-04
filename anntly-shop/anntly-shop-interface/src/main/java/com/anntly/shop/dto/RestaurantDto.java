package com.anntly.shop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: RestaurantDto
 * @ProjectName anntly
 * @Description: 餐厅 Dto
 * @date 2019/1/2722:23
 */
@Data
public class RestaurantDto {


    private Long id;

    private String name;

    private String description;

    private Integer pid;

    private Integer cid;

    private Integer aid;

    private Integer nid;

    // 省 市 区
    @Transient
    private List<Integer> ids;

    // 详细位置(插入的时候加入省/市/...)
    private String address;

    private String phone;

    // 店铺头像(1张)
    private String pic;

    // 店铺实景(多张 "," 隔开)
    private String photo;

    @Transient
    private String[] photos;

    // 主营菜品
    private String mainFoods;

    // 评价星级(不可修改)
    private Integer star;

    // 人均消费(不可修改)
    private BigDecimal avg;

    // 营业时间
    @JsonFormat(pattern="HH:mm:ss")
    private Date beginTime;

    // 关门时间
    @JsonFormat(pattern="HH:mm:ss")
    private Date endTime;

    // 执照图片(多张)
    private String license;

    @Transient
    private String[] licenses;

    // 餐厅状态(0 休息中 1 正常营业 2 封禁)
    private Integer status;

    // 配送范围(公里)
    private BigDecimal deliveryArea;

    public void initDatas(){
        if(this.nid != null && this.nid != 0){
            this.ids = Arrays.asList(this.pid,this.cid,this.aid,this.nid);
        }
        this.ids = Arrays.asList(this.pid,this.cid,this.aid);
        if(null!=this.photo){
            this.photos = photo.split(",");
        }
        if(null!=this.license){
            this.licenses = license.split(",");
        }
    }
}
