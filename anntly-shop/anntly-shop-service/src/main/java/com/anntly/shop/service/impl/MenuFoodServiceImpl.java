package com.anntly.shop.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.mapper.MenuFoodMapper;
import com.anntly.shop.pojo.MenuFood;
import com.anntly.shop.service.MenuFoodService;
import com.anntly.shop.vo.MenuFoodParams;
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
 * @Title: MenuFoodServiceImpl
 * @ProjectName anntly
 * @Description: MenuFoodServiceImpl
 * @date 2019/2/1211:23
 */
@Service
public class MenuFoodServiceImpl implements MenuFoodService {

    @Autowired
    private MenuFoodMapper menuFoodMapper;


    @Override
    public PageResult<MenuFood> queryPage(PageRequest pageRequest, MenuFoodParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<MenuFood> menuFoods = menuFoodMapper.queryPage(params);
        PageInfo<MenuFood> pageInfo = new PageInfo<>(menuFoods);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),menuFoods);
    }

    @Override
    @Transactional
    public void changeSalable(Long id) {
        int count = menuFoodMapper.changeSalable(id);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void changeRecommend(Long id) {
        int count = menuFoodMapper.changeRecommend(id);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void saveMenuFood(MenuFood menuFood) {
        menuFood.setCreateTime(new Date());
        menuFood.setUpdateTime(menuFood.getCreateTime());
        menuFood.setDataStatus(true);
        menuFood.setStatus(true);
        menuFood.setStar(0);
        menuFood.setMonthAmount(0);
        menuFoodMapper.insert(menuFood);
    }

    @Override
    @Transactional
    public void updateMenuFood(MenuFood menuFood) {
        menuFood.setUpdateTime(new Date());
        int i = menuFoodMapper.updateByPrimaryKeySelective(menuFood);
        if(i<=0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void deleteFood(Long id) {
        MenuFood menuFood = new MenuFood();
        menuFood.setId(id);
        menuFood.setDataStatus(false);
        menuFoodMapper.updateByPrimaryKeySelective(menuFood);
    }

    @Override
    @Transactional
    public void deleteFoods(List<Long> ids) {
        menuFoodMapper.updateBatch(ids);
    }

    @Override
    public List<Long> queryFoodsByCid(List<Long> cids) {
        List<Long> ids = menuFoodMapper.queryFoodsByCid(cids);
        if(CollectionUtils.isEmpty(ids)){
            throw new AnnException(ExceptionEnum.FOODS_NOT_FOUND);
        }
        return ids;
    }
}
