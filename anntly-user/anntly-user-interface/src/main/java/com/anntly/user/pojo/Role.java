package com.anntly.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author soledad
 * @Title: Role
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2110:39
 */
@Data
@Table(name = "tb_role")
public class Role implements GrantedAuthority {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private String description;

    @JsonProperty("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonProperty("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Override
    public String getAuthority() {
        return name;
    }
}
