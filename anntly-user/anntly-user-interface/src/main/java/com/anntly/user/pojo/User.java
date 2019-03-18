package com.anntly.user.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: User
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2110:21
 */
@Data
@Table(name = "tb_user")
public class User implements UserDetails, Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String icon;

    private Boolean member;

    private Boolean userStatus;

    private Date registerTime;

    private Date updateTime;

    private Date lastLoginTime;

    private Boolean dataStatus;

    @Transient
    private List<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permission> authorities = new ArrayList<>();
        for (Role role : this.roles) {
            authorities.addAll(role.getAuthorities());
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
