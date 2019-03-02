package com.anntly.shop.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.Menu;
import com.anntly.shop.vo.MenuParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author soledad
 * @Title: MenuMapper
 * @ProjectName anntly
 * @Description: MenuMapper
 * @date 2019/2/410:41
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取 餐厅page
     * @param params
     * @return
     */
    List<Menu> queryPage(@Param("params") MenuParams params);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<Long> ids);

    @Select("select id,name from tb_menu where restaurant_id = #{restaurantId}")
    List<Node> queryNodesByRid(@Param("restaurantId") Long restaurantId);
}
