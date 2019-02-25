package com.anntly.user.pojo;

import lombok.Data;

/**
 * @author soledad
 * @Title: JWT
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2111:31
 */
@Data
public class JWT {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String jti;
}
