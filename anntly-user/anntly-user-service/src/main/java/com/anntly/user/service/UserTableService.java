package com.anntly.user.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.user.dto.UserTable;
import com.anntly.user.vo.UserParam;

/**
 * @author soledad
 * @Title: UserTableService
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1815:49
 */
public interface UserTableService {
    PageResult<UserTable> queryPage(PageRequest pageRequest, UserParam params);

    void saveUser(UserTable userTable);

    void updateUser(UserTable userTable);

    void deleteUser(Long id);
}
