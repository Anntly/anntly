package com.anntly.shop.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.mapper.MenuMapper;
import com.anntly.shop.pojo.Menu;
import com.anntly.shop.service.MenuCatService;
import com.anntly.shop.service.MenuService;
import com.anntly.shop.vo.MenuParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: MenuServiceImpl
 * @ProjectName anntly
 * @Description: MenuServiceImpl
 * @date 2019/2/49:31
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuCatService menuCatService;


    @Override
    public PageResult<Menu> queryPage(PageRequest pageRequest, MenuParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<Menu> menus = menuMapper.queryPage(params);
        PageInfo<Menu> pageInfo = new PageInfo<>(menus);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),menus);
    }

    @Override
    @Transactional
    public void saveMenu(Menu menu) {
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        menu.setDataStatus(true);
        menuMapper.insert(menu);
    }

    @Override
    @Transactional
    public void updateMenu(Menu menu) {
        menu.setUpdateTime(new Date());
        int count = menuMapper.updateByPrimaryKeySelective(menu);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        // 获取menu下的所有分类与菜品
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<Long> cids = menuCatService.queryCatIdsByMid(ids);
        menuCatService.deleteMenuCats(cids);

        Menu menu = new Menu();
        menu.setId(id);
        menu.setDataStatus(false);
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    @Transactional
    public void deleteMenus(List<Long> ids) {
        List<Long> cids = menuCatService.queryCatIdsByMid(ids);
        menuCatService.deleteMenuCats(cids);

        menuMapper.updateBatch(ids);
    }
}
