package com.anntly.shop.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: Expend
 * @ProjectName anntly
 * @Description: 支出类
 * @date 2019/1/2419:52
 */
@Data
@Table(name = "tb_expend")
public class Expend {

    @Id
    private String id;

    private BigDecimal amount;

    private String content;

    private String employeeCode;

    private String employeeName;

    private Boolean dataStatus;

    private Date createTime;

    private Date updateTime;
}
