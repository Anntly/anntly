package com.anntly.user.vo;

import lombok.Data;

/**
 * @author soledad
 * @Title: UserParam
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1815:50
 */
@Data
public class UserParam {

    private Long id;

    private String username;

    private String phone;

    private String email;

    private Boolean member;

    // 用户状态 0 封禁 1 正常
    private Boolean userStatus;

    // 根据权限查询用户
    private Long roleId;

    private String sname;

    private String sord;
}
