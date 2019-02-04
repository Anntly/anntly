package com.anntly.common.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author soledad
 * @Title: Area
 * @ProjectName anntly
 * @Description: 地区实体类
 * @date 2019/1/2416:20
 */
@Data
@Table(name = "area")
public class Area {

    @Id
    @JsonProperty("value")
    private Integer id;

    @JsonProperty("label")
    private String name;

    private Integer pid;

    @Transient
    private List<Area> children;
}
