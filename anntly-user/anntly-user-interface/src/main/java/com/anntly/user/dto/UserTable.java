package com.anntly.user.dto;

import com.anntly.user.pojo.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: UserTable
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1815:36
 */
@Data
public class UserTable {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String icon;

    private Boolean member;

    // 用户状态 0 封禁 1 正常
    private Boolean userStatus;

    @JsonProperty("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    @JsonProperty("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonProperty("last_login_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private Boolean dataStatus;

    @Transient
    private String roles;

}
