package com.anntly.dish.web;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.dish.pojo.Category;
import com.anntly.dish.service.CategoryService;
import com.anntly.dish.vo.CategoryOptions;
import com.anntly.dish.vo.Node;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author soledad
 * @Title: CategoryController
 * @ProjectName anntly
 * @Description:
 * @date 2019/1/1721:05
 */
@RestController
@RequestMapping("/category")
@Api(value = "category-controller",description = "菜品分类查询接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('QUERY_CAT')")
    @ApiOperation(value="获取菜品分类列表", notes="命名需要与数据库对应")
    public ResponseEntity<List<Category>> queryCategoryPage(){
        return ResponseEntity.ok(categoryService.queryCategoryPage());
    }

    @GetMapping("/cas")
    @ApiOperation(value="获取菜品分类列表(级联模式)", notes="命名需要与数据库对应")
    // TODO 待优化 与分类信息查询做整合
    public ResponseEntity<List<CategoryOptions>> queryCategoryCas(){
        return ResponseEntity.ok(categoryService.queryCategoryCas());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADD_CAT')")
    @ApiOperation(value="添加菜品分类", notes="category的pid为添加的节点的父节点ID")
    public ResponseEntity<Void> saveCategory(@Validated Category category){
        if(null == category){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_CAT')")
    @ApiOperation(value="修改菜品分类信息", notes="命名需要与数据库对应")
    public ResponseEntity<Void> updateCategory(Category category){
        System.err.println(category.toString());
        if(null == category){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        categoryService.updateCategory(category);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DELETE_CAT')")
    @ApiOperation(value="删除菜品分类信息", notes="需要传递删除节点的ID")
    public ResponseEntity<Void> deleteCategory(@RequestParam("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/ids/{id}")
    @ApiOperation(value="根据分类id获取父分类idList", notes="发送为最底层ID")
    public ResponseEntity<List<Long>> queryIdsById(@PathVariable("id") Long id){
        if(null == id){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        return ResponseEntity.ok(categoryService.queryIdsById(id));
    }

    @GetMapping("/nodes")
    @ApiOperation(value="获取所有底层noede", notes="无")
    public ResponseEntity<List<Node>> queryNodes(){
        return ResponseEntity.ok(categoryService.queryNodes());
    }
}
