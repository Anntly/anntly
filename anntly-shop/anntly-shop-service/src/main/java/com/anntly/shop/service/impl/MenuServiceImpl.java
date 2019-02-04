package com.anntly.shop.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.mapper.MenuMapper;
import com.anntly.shop.pojo.Menu;
import com.anntly.shop.service.MenuService;
import com.anntly.shop.vo.MenuParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public PageResult<Menu> queryPage(PageRequest pageRequest, MenuParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<Menu> menus = menuMapper.queryPage(params);
        PageInfo<Menu> pageInfo = new PageInfo<>(menus);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),menus);
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        int count = menuMapper.updateByPrimaryKeySelective(menu);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }
}
