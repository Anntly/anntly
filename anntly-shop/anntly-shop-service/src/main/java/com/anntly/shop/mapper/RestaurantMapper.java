package com.anntly.shop.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.shop.dto.RestaurantNode;
import com.anntly.shop.pojo.Restaurant;
import com.anntly.shop.vo.RestaurantParams;
import com.anntly.shop.dto.RestaurantDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author soledad
 * @Title: RestaurantMapper
 * @ProjectName anntly
 * @Description: RestaurantMapper
 * @date 2019/1/2420:48
 */
public interface RestaurantMapper extends BaseMapper<Restaurant> {

    /**
     * 获取 餐厅page
     * @param params
     * @param userId
     * @return
     */
    List<RestaurantDto> queryPage(@Param("params") RestaurantParams params, @Param("userId") Long userId);

    /**
     * 删除 餐厅
     * @param id
     * @return
     */
    @Update("update tb_restaurant set data_status = not data_status where id = #{id}")
    int deleteRestaurant(@Param("id") Long id);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<Long> ids);

    @Select("select id,name from tb_restaurant where user_id = #{userId} and data_status = 1")
    List<RestaurantNode> queryNodes(@Param("userId") Long userId);
}
