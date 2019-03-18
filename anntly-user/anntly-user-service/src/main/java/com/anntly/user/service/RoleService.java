package com.anntly.user.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.user.dto.Node;
import com.anntly.user.pojo.Role;
import com.anntly.user.vo.RoleParam;
import com.anntly.user.vo.RoleVo;

import java.util.List;

/**
 * @author soledad
 * @Title: RoleService
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1713:34
 */
public interface RoleService {
    PageResult<Role> queryPage(PageRequest pageRequest, RoleParam params);

    void saveRole(RoleVo roleVo);

    void updateRole(RoleVo roleVo);

    void deleteRole(Long id);

    List<Node> queryPermissionByRid(Long id);

    List<Node> queryRolesByUserId(Long userId);

    void removeUserRole(Long userId);
}
