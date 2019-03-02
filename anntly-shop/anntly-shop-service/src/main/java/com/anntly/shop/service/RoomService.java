package com.anntly.shop.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.pojo.Room;
import com.anntly.shop.vo.RoomParams;

import java.util.List;

/**
 * @author soledad
 * @Title: RoomService
 * @ProjectName anntly
 * @Description: RoomService
 * @date 2019/2/2821:28
 */
public interface RoomService {
    PageResult<Room> queryPage(PageRequest pageRequest, RoomParams params);

    void saveRoom(Room room);

    void updateRoom(Room room);

    void deleteRoom(Long id);

    void deleteRooms(List<Long> ids);
}
