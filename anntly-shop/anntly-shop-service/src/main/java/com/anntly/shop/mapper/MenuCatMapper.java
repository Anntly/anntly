package com.anntly.shop.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.MenuCategory;
import com.anntly.shop.vo.MenuCatParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author soledad
 * @Title: MenuCatMapper
 * @ProjectName anntly
 * @Description: MenuCatMapper
 * @date 2019/2/916:30
 */
public interface MenuCatMapper extends BaseMapper<MenuCategory> {

    /**
     * 获取 菜品分类page
     * @param params
     * @return
     */
    List<MenuCategory> queryPage(@Param("params") MenuCatParams params);

    @Select("select id,name from tb_menu_food_cat where menu_id = #{menuId}")
    List<Node> queryCatsByMenuId(@Param("menuId") Long menuId);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<Long> ids);

    List<Long> queryCatIdsByMid(@Param("idList") List<Long> mids);
}
