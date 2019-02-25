package com.anntly.user.pojo;

import lombok.Data;

/**
 * @author soledad
 * @Title: AuthToken
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2123:05
 */
@Data
public class AuthToken {

    private String accessToken;

    private String refresh_token;

    private String jwt_token;
}
