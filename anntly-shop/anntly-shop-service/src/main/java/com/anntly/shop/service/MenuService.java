package com.anntly.shop.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.pojo.Menu;
import com.anntly.shop.vo.MenuParams;

import java.util.List;

/**
 * @author soledad
 * @Title: MenuService
 * @ProjectName anntly
 * @Description: MenuService
 * @date 2019/2/49:31
 */
public interface MenuService {

    PageResult<Menu> queryPage(PageRequest pageRequest, MenuParams params);

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Long id);

    void deleteMenus(List<Long> ids);
}
