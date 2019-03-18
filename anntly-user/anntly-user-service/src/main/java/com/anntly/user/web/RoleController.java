package com.anntly.user.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.JsonUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.user.dto.Node;
import com.anntly.user.pojo.Role;
import com.anntly.user.service.RoleService;
import com.anntly.user.vo.RoleParam;
import com.anntly.user.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author soledad
 * @Title: RoleController
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/1713:34
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<Role>> queryMenuPage(PageRequest pageRequest){
        RoleParam params = null;
        if(null == pageRequest){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        if(null != pageRequest.getKey()){
            params = JsonUtils.parse(pageRequest.getKey(), RoleParam.class);
        }
        if(null != pageRequest.getSortBy() && null != pageRequest.getDesc()){
            params.setSname(pageRequest.getSortBy());
            params.setSord(pageRequest.getDesc()?" desc":" asc");
        }
        return ResponseEntity.ok(roleService.queryPage(pageRequest,params));
    }

    @PostMapping
    public ResponseEntity<Void> saveRole(RoleVo roleVo){
        if(null == roleVo){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        roleService.saveRole(roleVo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateRole(RoleVo roleVo){
        if(null == roleVo){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        roleService.updateRole(roleVo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long id){
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/permission")
    public ResponseEntity<List<Node>> queryPermissionByRid(@RequestParam(value = "id",required = false) Long id){

        return ResponseEntity.ok(roleService.queryPermissionByRid(id));
    }

    @GetMapping("/userId")
    public ResponseEntity<List<Node>> queryRolesByUserId(@RequestParam(value = "userId",required = false) Long userId){

        return ResponseEntity.ok(roleService.queryRolesByUserId(userId));
    }
}
