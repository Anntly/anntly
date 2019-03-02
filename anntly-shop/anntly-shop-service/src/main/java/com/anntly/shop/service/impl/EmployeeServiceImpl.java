package com.anntly.shop.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.mapper.EmployeeMapper;
import com.anntly.shop.pojo.Employee;
import com.anntly.shop.service.EmployeeService;
import com.anntly.shop.vo.EmployeeParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: EmployeeServiceImpl
 * @ProjectName anntly
 * @Description:
 * @date 2019/2/2714:01
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageResult<Employee> queryPage(PageRequest pageRequest, EmployeeParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<Employee> menuFoods = employeeMapper.queryPage(params);
        PageInfo<Employee> pageInfo = new PageInfo<>(menuFoods);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),menuFoods);
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        employee.setUpdateTime(new Date());
        employee.setStatus(1);
        employeeMapper.insert(employee);
    }

    @Override
    @Transactional
    public void updateEmployee(Employee employee) {
        employee.setUpdateTime(new Date());
        int i = employeeMapper.updateByPrimaryKeySelective(employee);
        if(i<=0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public List<Node> getEmployeesByRid(Long resturantId) {
        Employee employee = new Employee();
        employee.setRestaurantId(resturantId);
        List<Node> nodes = employeeMapper.queryEmployeesByRid(resturantId);
        if(CollectionUtils.isEmpty(nodes)){
            throw new AnnException(ExceptionEnum.Employees_NOT_FOUND);
        }
        return nodes;
    }
}
