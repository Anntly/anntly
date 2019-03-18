package com.anntly.user.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.user.dto.Node;
import com.anntly.user.pojo.Role;
import com.anntly.user.pojo.RolePermission;
import com.anntly.user.vo.RoleParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author soledad
 * @Title: RoleMapper
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1713:35
 */
public interface RoleMapper extends BaseMapper<Role> {


    List<Role> queryPage(@Param("params") RoleParam params);

    int insertRolePermissionBatch(@Param("list") List<RolePermission> list);

    @Insert("insert into tb_role(name,description,create_time,update_time) value (#{name},#{name},#{createTime},#{updateTime})")
    @Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
    Long saveRole(Role role);

    void removePermissionByRoleId(@Param("roleId") Long roleId);

    List<Node> queryPermissionByRid(@Param("roleId") Long id);

    List<Node> queryRolesByUserId(@Param("userId") Long userId);

    @Delete("delete from tb_user_role where user_id = #{userId}")
    void removeUserRole(@Param("userId") Long userId);
}
