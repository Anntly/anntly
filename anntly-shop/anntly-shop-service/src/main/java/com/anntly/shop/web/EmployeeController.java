package com.anntly.shop.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.Employee;
import com.anntly.shop.service.EmployeeService;
import com.anntly.shop.vo.EmployeeParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author soledad
 * @Title: EmployeeController
 * @ProjectName anntly
 * @Description: 员工管理Controller
 * @date 2019/2/2713:58
 */
@RestController
@RequestMapping("/employee")
@Api(value = "employee-controller",description = "员工controller")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('QUERY_EMPLOYEE')")
    @ApiOperation(value="获取餐厅列表", notes="命名需要与数据库对应")
    public ResponseEntity<PageResult<Employee>> queryRestaurantPage(PageRequest pageRequest){
        // 需要与 餐厅绑定
        EmployeeParams params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), EmployeeParams.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(employeeService.queryPage(pageRequest,params));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADD_EMPLOYEE')")
    @ApiOperation(value="增添员工", notes="与餐厅Id绑定")
    public ResponseEntity<Void> saveRestaurant(Employee employee){
        if(null == employee){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_EMPLOYEE')")
    @ApiOperation(value="修改员工信息", notes="与餐厅Id绑定")
    public ResponseEntity<Void> updateRestaurant(Employee employee){
        if(null == employee){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        employeeService.updateEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/re")
    @ApiOperation(value="根据餐厅Id获取员工", notes="与餐厅Id绑定")
    public ResponseEntity<List<Node>> getEmployeesByRid(@Param("resturantId") Long resturantId){
        if(null == resturantId){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(employeeService.getEmployeesByRid(resturantId));
    }

}
