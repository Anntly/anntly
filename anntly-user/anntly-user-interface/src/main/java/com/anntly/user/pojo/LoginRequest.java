package com.anntly.user.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author soledad
 * @Title: LoginRequest
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2222:47
 */
@Data
public class LoginRequest {

    @NotEmpty
    @Length(min = 2,max = 32,message = "用户名长度必须在4-32位")
    private String username;

    @Length(min = 4,max = 32,message = "密码长度必须在4-32位")
    private String password;
}
