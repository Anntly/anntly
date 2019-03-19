package com.anntly.user.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.user.dto.UserTable;
import com.anntly.user.mapper.UserMapper;
import com.anntly.user.pojo.User;
import com.anntly.user.pojo.UserRole;
import com.anntly.user.service.RoleService;
import com.anntly.user.service.UserService;
import com.anntly.user.service.UserTableService;
import com.anntly.user.utils.BPwdEncoderUtil;
import com.anntly.user.vo.UserParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: UserTableServiceImpl
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1815:49
 */
@Service
public class UserTableServiceImpl implements UserTableService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public PageResult<UserTable> queryPage(PageRequest pageRequest, UserParam params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<UserTable> users = userMapper.queryPage(params);
        PageInfo<UserTable> pageInfo = new PageInfo<>(users);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),users);
    }

    @Override
    @Transactional
    public void saveUser(UserTable userTable) {
        User user = new User();
        BeanUtils.copyProperties(userTable,user);
        user.setUpdateTime(new Date());
        user.setRegisterTime(user.getUpdateTime());
        user.setLastLoginTime(user.getUpdateTime());
        user.setDataStatus(true);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(user.getPassword()));

        // 判断用户名是否存在
        if(userService.checkUser(user.getUsername(),user.getPhone(),user.getEmail())){
            throw new AnnException(ExceptionEnum.User_EXIST);
        }


        userMapper.insert(user);

        List<Long> roles = JsonUtils.parseList(userTable.getRoles(), Long.class);

        List<UserRole> userRoles = new ArrayList<>();
        // 用户角色管理表
        if(!CollectionUtils.isEmpty(roles)){

            for (Long roleId : roles) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRoles.add(userRole);
            }
            userMapper.insertUserRoleBatch(userRoles);
        }

    }

    @Override
    @Transactional
    public void updateUser(UserTable userTable) {
        User user = new User();
        BeanUtils.copyProperties(userTable,user);
        user.setPassword(null);
        user.setUpdateTime(new Date());
        // 判断用户名是否存在
        if(userService.checkUser(user.getUsername(),user.getPhone(),user.getEmail())){
            throw new AnnException(ExceptionEnum.User_EXIST);
        }
        userMapper.updateByPrimaryKeySelective(user);
        // 删除角色用户关联
        roleService.removeUserRole(user.getId());
        // 重新添加
        List<Long> roles = JsonUtils.parseList(userTable.getRoles(), Long.class);
        List<UserRole> userRoles = new ArrayList<>();
        // 用户角色管理表
        if(!CollectionUtils.isEmpty(roles)){

            for (Long roleId : roles) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRoles.add(userRole);
            }
            userMapper.insertUserRoleBatch(userRoles);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setDataStatus(false);
        userMapper.updateByPrimaryKeySelective(user);
        roleService.removeUserRole(id);
    }
}
