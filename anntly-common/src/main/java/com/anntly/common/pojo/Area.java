package com.anntly.common.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

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
    private Integer Id;

    private String Name;

    private Integer Pid;
}
