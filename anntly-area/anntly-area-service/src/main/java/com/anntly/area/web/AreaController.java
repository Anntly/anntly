package com.anntly.area.web;

import com.anntly.area.service.AreaService;
import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.pojo.Area;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author soledad
 * @Title: AreaController
 * @ProjectName anntly
 * @Description: AreaController
 * @date 2019/2/115:54
 */
@RestController
@Api(value = "area-controller",description = "地域查询接口")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @GetMapping
    @ApiOperation(value="查询所有分类", notes="不提供地区增删改")
    public ResponseEntity<List<Area>> queryAreas(){
        return ResponseEntity.ok(areaService.queryAreas());
    }

    @GetMapping("/addr")
    @ApiOperation(value="查询所有分类", notes="不提供地区增删改")
    public ResponseEntity<String> queryAddr(@RequestParam("ids") List<Integer> ids){
        // 根据ids查询地址
        return ResponseEntity.ok(areaService.queryAddr(ids));
    }

//    @GetMapping("/id/{id}")
//    @ApiOperation(value="根据id查询children", notes="不提供地区增删改")
//    public ResponseEntity<List<Area>> queryChildren(@PathVariable("id") Integer id){
//        if(null == id){
//            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
//        }
//        return ResponseEntity.ok(areaService.queryChildren(id));
//    }
}
