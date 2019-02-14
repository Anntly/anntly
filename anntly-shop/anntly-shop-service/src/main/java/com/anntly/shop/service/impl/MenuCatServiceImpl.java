package com.anntly.shop.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.mapper.MenuCatMapper;
import com.anntly.shop.pojo.MenuCategory;
import com.anntly.shop.service.MenuCatService;
import com.anntly.shop.service.MenuFoodService;
import com.anntly.shop.vo.MenuCatParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: MenuCatServiceImpl
 * @ProjectName anntly
 * @Description: MenuCatServiceImpl
 * @date 2019/2/916:30
 */
@Service
public class MenuCatServiceImpl  implements MenuCatService {

    @Autowired
    private MenuCatMapper menuCatMapper;

    @Autowired
    private MenuFoodService menuFoodService;

    @Override
    public PageResult<MenuCategory> queryPage(PageRequest pageRequest, MenuCatParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<MenuCategory> menuCats = menuCatMapper.queryPage(params);
        PageInfo<MenuCategory> pageInfo = new PageInfo<>(menuCats);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),menuCats);
    }

    @Override
    public void saveMenuCategory(MenuCategory menuCategory) {
        menuCategory.setCreateTime(new Date());
        menuCategory.setUpdateTime(menuCategory.getCreateTime());
        menuCategory.setDataStatus(true);
        menuCatMapper.insert(menuCategory);
    }

    @Override
    @Transactional
    public void updateMenuCategory(MenuCategory menuCategory) {
        menuCategory.setUpdateTime(new Date());
        int count = menuCatMapper.updateByPrimaryKeySelective(menuCategory);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public List<Node> queryCatsByMenuId(Long menuId) {
        List<Node> nodes = menuCatMapper.queryCatsByMenuId(menuId);
        if(CollectionUtils.isEmpty(nodes)){
            throw new AnnException(ExceptionEnum.NOT_FOUND);
        }
        return nodes;
    }

    @Override
    @Transactional
    public void deleteMenuCat(Long id) {
        // 先获取该分类下所有的菜品
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<Long> dishIds = menuFoodService.queryFoodsByCid(ids);
        // 删除分类下所有菜品
        menuFoodService.deleteFoods(dishIds);
        // 删除分类
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setId(id);
        menuCategory.setDataStatus(false);
        menuCatMapper.updateByPrimaryKeySelective(menuCategory);
    }

    @Override
    @Transactional
    public void deleteMenuCats(List<Long> ids) {
        // 删除分类下所有菜品
        List<Long> dishIds = menuFoodService.queryFoodsByCid(ids);
        // 删除分类下所有菜品
        menuFoodService.deleteFoods(dishIds);
        // 删除分类
        menuCatMapper.updateBatch(ids);
    }

    @Override
    public List<Long> queryCatIdsByMid(List<Long> mids) {
        List<Long> cids = menuCatMapper.queryCatIdsByMid(mids);
        if(CollectionUtils.isEmpty(cids)){
            throw new AnnException(ExceptionEnum.FOODS_NOT_FOUND);
        }
        return cids;
    }
}
