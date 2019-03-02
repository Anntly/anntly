package com.anntly.user.dto;

import lombok.Data;

import java.util.List;

/**
 * @author soledad
 * @Title: UserLoginDto
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2111:32
 */
@Data
public class UserLoginDto {

    private String token; //返回的token

    private String reToken; // refreshToken

    private String username;

    private String avatar;

    private List<String> roles;
}
