package com.anntly.shop.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.Employee;
import com.anntly.shop.vo.EmployeeParams;

import java.util.List;

/**
 * @author soledad
 * @Title: EmployeeService
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2714:00
 */
public interface EmployeeService {

    PageResult<Employee> queryPage(PageRequest pageRequest, EmployeeParams params);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    List<Node> getEmployeesByRid(Long resturantId);
}
