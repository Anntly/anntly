package com.anntly.shop.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.Desk;
import com.anntly.shop.vo.DeskParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author soledad
 * @Title: DeskMapper
 * @ProjectName anntly
 * @Description: DeskMapper
 * @date 2019/3/121:49
 */
public interface DeskMapper extends BaseMapper<Desk> {

    List<Desk> queryPage(@Param("params") DeskParams params);

    @Select("select id from tb_desk where room_id = #{roomId}")
    List<Long> queryDesksByRoomId(@Param("roomId") Long roomId);

    List<Long> queryDeskIdsByMid(@Param("idList") List<Long> mids);

    @Select("select id,name from tb_desk where restaurant_id = #{restaurantId}")
    List<Node> queryDeskNodesByRid(@Param("restaurantId") Long restaurantId);
}
