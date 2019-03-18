package com.anntly.user.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author soledad
 * @Title: RolePermission
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1714:11
 */
@Data
@Table(name = "tb_role_permission")
public class RolePermission {

    @Id
    private Long roleId;

    @Id
    private Long permissionId;
}
