package com.anntly.shop.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.order.dto.Stock;
import com.anntly.shop.dto.FoodDto;
import com.anntly.shop.dto.Node;
import com.anntly.shop.dto.OrderDto;
import com.anntly.shop.dto.OrderFood;
import com.anntly.shop.pojo.MenuFood;
import com.anntly.shop.vo.MenuFoodParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author soledad
 * @Title: MenuFoodMapper
 * @ProjectName anntly
 * @Description: MenuFoodMapper
 * @date 2019/2/1211:31
 */
public interface MenuFoodMapper extends BaseMapper<MenuFood> {

    List<MenuFood> queryPage(@Param("params") MenuFoodParams params);

    @Update("update tb_menu_food set status = not status where id = #{id}")
    int changeSalable(@Param("id") Long id);

    @Update("update tb_menu_food set recommend = not recommend where id = #{id}")
    int changeRecommend(@Param("id") Long id);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<Long> ids);

    int updateStock(@Param("list")List<Stock> stocks);

    List<Long> queryFoodsByCid(@Param("idList") List<Long> cids);

    @Select("select id,show_name,price,discount from tb_menu_food where m_cid = #{mCid}")
    List<FoodDto> queryNodesByCid(@Param("mCid") Long mCid);

    List<OrderDto> queryOrderDtosByMenuId(@Param("menuId") Long menuId);

    @Select("select id,m_cid,show_name,price,discount,pic from tb_menu_food where menu_id = #{menuId} and recommend = 1")
    List<OrderFood> queryRecommendedFoods(@Param("menuId") Long menuId);


}
