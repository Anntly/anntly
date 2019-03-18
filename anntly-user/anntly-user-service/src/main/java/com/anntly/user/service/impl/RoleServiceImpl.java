package com.anntly.user.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.user.dto.Node;
import com.anntly.user.mapper.RoleMapper;
import com.anntly.user.pojo.Role;
import com.anntly.user.pojo.RolePermission;
import com.anntly.user.service.RoleService;
import com.anntly.user.vo.RoleParam;
import com.anntly.user.vo.RoleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: RoleServiceImpl
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1713:35
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageResult<Role> queryPage(PageRequest pageRequest, RoleParam params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<Role> roles = roleMapper.queryPage(params);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),roles);
    }

    @Override
    @Transactional
    public void saveRole(RoleVo roleVo) {

        Role role = new Role();
        role.setName(roleVo.getName());
        role.setDescription(roleVo.getDescription());
        role.setCreateTime(new Date());
        role.setUpdateTime(role.getCreateTime());
        roleMapper.saveRole(role);
        List<Long> permissions = JsonUtils.parseList(roleVo.getPermissions(), Long.class);
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Long permissionId : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }
        roleMapper.insertRolePermissionBatch(rolePermissions);
    }

    @Override
    @Transactional
    public void updateRole(RoleVo roleVo) {
        Role role = new Role();
        role.setId(roleVo.getId());
        role.setName(roleVo.getName());
        role.setDescription(roleVo.getDescription());
        role.setUpdateTime(new Date());
        List<Long> permissions = JsonUtils.parseList(roleVo.getPermissions(), Long.class);
        // 先删除该role中的所有permission
        roleMapper.removePermissionByRoleId(role.getId());

        // 重新插入permission
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Long permissionId : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }
        roleMapper.insertRolePermissionBatch(rolePermissions);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        // 删除关联表信息
        roleMapper.removePermissionByRoleId(id);
        // 删除role
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Node> queryPermissionByRid(Long id) {
        List<Node> nodes = roleMapper.queryPermissionByRid(id);
        if(CollectionUtils.isEmpty(nodes)){
            throw new AnnException(ExceptionEnum.Permission_NOT_FOUND);
        }
        return nodes;
    }

    @Override
    public List<Node> queryRolesByUserId(Long userId) {
        List<Node> nodes = roleMapper.queryRolesByUserId(userId);
        if(CollectionUtils.isEmpty(nodes)){
            throw new AnnException(ExceptionEnum.Roles_NOT_FOUND);
        }
        return nodes;
    }

    @Override
    @Transactional
    public void removeUserRole(Long userId) {
        roleMapper.removeUserRole(userId);
    }
}
