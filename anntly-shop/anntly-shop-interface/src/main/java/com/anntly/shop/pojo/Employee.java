package com.anntly.shop.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author soledad
 * @Title: Employee
 * @ProjectName anntly
 * @Description: 员工实体类
 * @date 2019/1/2417:45
 */
@Data
@Table(name = "tb_employee")
public class Employee {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private Long restaurantId;

    private String position;

    private String idCardNumber;

    private Boolean sex;

    private Integer age;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date birthday;

    private String phone;

    private BigDecimal salary;

    @JsonProperty("create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date createTime;

    @JsonProperty("expire_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date expireTime;

    @JsonProperty("update_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateTime;

    // 员工状态
    private Integer status;
}
