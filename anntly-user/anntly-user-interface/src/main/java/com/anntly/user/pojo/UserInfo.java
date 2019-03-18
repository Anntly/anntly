package com.anntly.user.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soledad
 * @Title: UserInfo
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1810:08
 */
@Data
@Table(name = "tb_user_info")
public class UserInfo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long userId;

    private Boolean sex;

    private Date birthday;

    private String nickName;

    private Integer age;

    private String underwrite;

    private String address;
}
