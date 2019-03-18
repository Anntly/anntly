package com.anntly.user.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.user.dto.UserDto;
import com.anntly.user.dto.UserTable;
import com.anntly.user.pojo.User;
import com.anntly.user.pojo.UserInfo;
import com.anntly.user.pojo.UserRole;
import com.anntly.user.vo.UserParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author soledad
 * @Title: UserMapper
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2110:51
 */
public interface UserMapper extends BaseMapper<User> {

    User findUserByUsername(@Param("username") String username);

    @Select("select id,username,password,member,phone,email,icon from tb_user where username = #{username}")
    UserDto getUserByUsername(@Param("username") String username);

    @Select("select * from tb_user_info where user_id = #{userId}")
    UserInfo getUserInfoByUserId(@Param("userId") Long userId);

    int updateUserInfo(@Param("info") UserInfo info);

    List<UserTable> queryPage(@Param("params") UserParam params);

    int insertUserRoleBatch(@Param("list") List<UserRole> list);
}
