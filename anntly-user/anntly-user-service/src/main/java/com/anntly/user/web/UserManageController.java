package com.anntly.user.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.user.dto.UserTable;
import com.anntly.user.service.UserTableService;
import com.anntly.user.vo.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author soledad
 * @Title: UserManageController
 * @ProjectName anntly
 * @Description: 用户管理Controller
 * @date 2019/3/1815:41
 */
@RestController
@RequestMapping("/manage")
public class UserManageController {

    @Autowired
    private UserTableService userTableService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<UserTable>> queryUserPage(PageRequest pageRequest){
        UserParam params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), UserParam.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname("u." + pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(userTableService.queryPage(pageRequest,params));
    }

    @PostMapping
    public ResponseEntity<Void> saveUser(UserTable userTable){
        if(null == userTable){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        userTableService.saveUser(userTable);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateRole(UserTable userTable){
        if(null == userTable){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        userTableService.updateUser(userTable);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id){
        userTableService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
