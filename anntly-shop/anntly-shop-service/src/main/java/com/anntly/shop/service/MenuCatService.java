package com.anntly.shop.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.MenuCategory;
import com.anntly.shop.vo.MenuCatParams;

import java.util.List;

/**
 * @author soledad
 * @Title: MenuCatService
 * @ProjectName anntly
 * @Description: MenuCatService
 * @date 2019/2/916:29
 */
public interface MenuCatService {
    PageResult<MenuCategory> queryPage(PageRequest pageRequest, MenuCatParams params);

    void saveMenuCategory(MenuCategory menuCategory);

    void updateMenuCategory(MenuCategory menuCategory);

    List<Node> queryCatsByMenuId(Long menuId);

    void deleteMenuCat(Long id);

    void deleteMenuCats(List<Long> ids);

    /**
     * 根据菜单ID获取所有菜品分类ID
     * @param mids
     * @return
     */
    List<Long> queryCatIdsByMid(List<Long> mids);
}
