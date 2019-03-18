package com.anntly.user.dto;

import lombok.Data;

/**
 * @author soledad
 * @Title: UserDto
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1721:09
 */
@Data
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private Boolean member;

    private String phone;

    private String email;

    private String icon;
}
