package com.anntly.user.service.impl;

import com.anntly.user.mapper.PermissionMapper;
import com.anntly.user.pojo.Permission;
import com.anntly.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: PermissionServiceImpl
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1713:58
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    @Transactional
    public void savePermission(Permission permission) {
        permission.setCreateTime(new Date());
        permission.setUpdateTime(permission.getCreateTime());
        permissionMapper.insert(permission);
    }

}
