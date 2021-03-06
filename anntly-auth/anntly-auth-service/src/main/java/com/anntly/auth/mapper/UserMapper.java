package com.anntly.auth.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.user.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author soledad
 * @Title: UserMapper
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2110:51
 */
public interface UserMapper extends BaseMapper<User> {

    User findUserByUsername(@Param("username") String username);
}
