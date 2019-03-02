package com.anntly.shop.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.shop.pojo.Room;
import com.anntly.shop.vo.RoomParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author soledad
 * @Title: RoomMapper
 * @ProjectName anntly
 * @Description: RoomMapper
 * @date 2019/3/116:20
 */
public interface RoomMapper extends BaseMapper<Room> {

    List<Room> queryPage(@Param("params")  RoomParams params);
}
