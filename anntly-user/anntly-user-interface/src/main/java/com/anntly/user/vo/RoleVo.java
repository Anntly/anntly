package com.anntly.user.vo;

import lombok.Data;

/**
 * @author soledad
 * @Title: RoleVo
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1714:14
 */
@Data
public class RoleVo {

    private Long id;

    private String name;

    private String description;

    private String permissions;
}
