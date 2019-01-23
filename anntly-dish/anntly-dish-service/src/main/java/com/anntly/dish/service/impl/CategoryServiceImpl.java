package com.anntly.dish.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.dish.mapper.CategoryMapper;
import com.anntly.dish.pojo.Category;
import com.anntly.dish.service.CategoryService;
import com.anntly.dish.vo.CategoryOptions;
import com.anntly.dish.vo.Node;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author soledad
 * @Title: CategoryServiceImpl
 * @ProjectName anntly
 * @Description:
 * @date 2019/1/1721:08
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryPage() {
        // 查询父节点的分类
        // TODO 加入Redis优化
        // TODO 算法优化：不使用递归多次查询，一次查询所有分类，再使用Map封装进行查询
        Category category = categoryMapper.queryParent();
        List<Category> categories = new ArrayList<>();
        getCategories(category);
        categories.add(category);
        return categories;
    }

    /**
     * 递归获取分类信息
     */
    public void getCategories(Category category){
        if(!category.getIsParent()){
            return;
        }
        List<Category> children = categoryMapper.queryChildren(category.getId());
        if(CollectionUtils.isEmpty(children)){
            return;
        }
        category.setChildren(children);
        for (Category child : children) {
            getCategories(child);
        }
    }

    @Override
    // TODO 与查询Category重复
    public List<CategoryOptions> queryCategoryCas() {
        // 获取所有第一层父节点
        List<CategoryOptions> categoryOptions = categoryMapper.queryChildrenOp(1L);
        for (CategoryOptions option : categoryOptions) {
            getOptions(option);
        }
        return categoryOptions;
    }

    @Override
    public List<Long> queryIdsById(Long id) {
        // 先获取category，判断是否为父节点
        Category category = categoryMapper.selectByPrimaryKey(id);
        if(category.getIsParent()){
            throw new AnnException(ExceptionEnum.PARAMETER_ERROR);
        }
        //递归获取其父节点
        List<Long> ids = new ArrayList<>();
        getIds(ids,category);
        Collections.reverse(ids);
        return ids;
    }

    @Override
    public List<Node> queryNodes() {
        return categoryMapper.queryNodes();
    }

    private void getIds(List<Long> ids, Category category) {
        if(category == null || category.getPid() == 0L){
            return;
        }
        ids.add(category.getId());
        getIds(ids,categoryMapper.selectByPrimaryKey(category.getPid()));
    }


    /**
     * 递归获取分类信息
     */
    public void getOptions(CategoryOptions options){
        if(!options.getIsParent()){
            return;
        }
        List<CategoryOptions> children = categoryMapper
                .queryChildrenOp(options.getId());
        if(CollectionUtils.isEmpty(children)){
            return;
        }
        options.setChildren(children);
        for (CategoryOptions child : children) {
            getOptions(child);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Category category) {
        // 根据主键更新不为null的属性
        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(Category category) {
        // 先获取父类节点
        boolean hasChild = true;
        Category parentCategory = categoryMapper.queryParentByPid(category.getPid());
        // 如果"父节点"不为空且父节点第一次添加子节点
        if(null != parentCategory && !parentCategory.getIsParent()){
            parentCategory.setIsParent(true);
            categoryMapper.updateByPrimaryKey(parentCategory);
            hasChild = false;
        }
        category.setIsParent(false);
        category.setDataStatus(true);
        // 获取已有的子节点中sort最大的值
        if(hasChild){
            int maxSort = categoryMapper.queryMaxSortByParentId(parentCategory.getId());
            category.setSort(++maxSort);
        }
        categoryMapper.insert(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        // 先查询该Category
        Category category = categoryMapper.selectByPrimaryKey(id);
        // 查询被删除节点的父节点的子节点数
        int count = categoryMapper.selectChildCount(category.getPid());
        // 如果被删除节点的父节点只有被删除节点一个子节点，修改其为子节点
        if(count <= 1){
            categoryMapper.changeParentStatus(category.getPid());
        }
        // 获取所有需要删除的节点的ID，做批量删除，减少与数据库交互的次数
        // 修改为批量更新 data_status 为0
        List<Long> ids = new ArrayList<>();
        findDeleteNodes(ids,category);
        categoryMapper.updateBatch(ids);
    }

    /**
     * 递归删除节点以及其子节点
     * @param ids category
     */
    public void findDeleteNodes(List<Long> ids,Category category){
        // 如果是父节点，需要递归删除所有子节点
        if(category.getIsParent()){
            // 查询所有子节点
            List<Category> children = categoryMapper.queryChildren(category.getId());
            for (Category child : children) {
                findDeleteNodes(ids,child);
            }
        }
        // 如果不是父节点，将id添加到要删除的ids列表中
        ids.add(category.getId());
    }

}
