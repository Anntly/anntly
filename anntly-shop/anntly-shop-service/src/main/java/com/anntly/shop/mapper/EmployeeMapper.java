package com.anntly.shop.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.Employee;
import com.anntly.shop.vo.EmployeeParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author soledad
 * @Title: EmployeeMapper
 * @ProjectName anntly
 * @Description: EmployeeMapper
 * @date 2019/2/2714:10
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Employee> queryPage(@Param("params") EmployeeParams params);

    @Select("select id,name from tb_employee where restaurant_id = #{resturantId}")
    List<Node> queryEmployeesByRid(@Param("resturantId") Long resturantId);
}
