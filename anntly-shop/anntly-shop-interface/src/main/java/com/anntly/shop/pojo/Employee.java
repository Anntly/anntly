package com.anntly.shop.pojo;

import lombok.Data;
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

    private String code;

    private String name;

    private Long restaurantId;

    private String position;

    private String idCardNumber;

    private Boolean sex;

    private Integer age;

    private Date birthday;

    private String phone;

    private BigDecimal salary;

    private Date createTime;

    private Date expireTime;

    private Date updateTime;

    // 员工状态
    private Integer status;
}
