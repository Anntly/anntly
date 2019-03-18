package com.anntly.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author soledad
 * @Title: UserInfoDto
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1810:14
 */
@Data
public class UserInfoDto {

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String icon;

    private Boolean sex;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date birthday;

    private String nickName;

    private Integer age;

    private String underwrite;

    private String address;
}
