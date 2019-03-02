package com.anntly.shop.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.mapper.RoomMapper;
import com.anntly.shop.pojo.Room;
import com.anntly.shop.service.DeskService;
import com.anntly.shop.service.RoomService;
import com.anntly.shop.vo.RoomParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: RoomServiceImpl
 * @ProjectName anntly
 * @Description: RoomServiceImpl
 * @date 2019/2/2821:29
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private DeskService deskService;

    @Override
    public PageResult<Room> queryPage(PageRequest pageRequest, RoomParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<Room> rooms = roomMapper.queryPage(params);
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),rooms);
    }

    @Override
    @Transactional
    public void saveRoom(Room room) {
        room.setCreateTime(new Date());
        room.setUpdateTime(room.getCreateTime());
        room.setStatus(true);
        roomMapper.insert(room);
    }

    @Override
    @Transactional
    public void updateRoom(Room room) {
        room.setUpdateTime(new Date());
        int count = roomMapper.updateByPrimaryKeySelective(room);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }

    }

    @Override
    @Transactional
    public void deleteRoom(Long id) {
        List<Long> desks = deskService.queryDesksByRoomId(id);
        if(!CollectionUtils.isEmpty(desks)){
            deskService.deleteDesks(desks);
        }
        roomMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void deleteRooms(List<Long> ids) {
        List<Long> dids = new ArrayList<>();
        if(!CollectionUtils.isEmpty(ids)){
            dids = deskService.queryDesksByRoomIds(ids);
            roomMapper.deleteByIdList(ids);
        }
        deskService.deleteDesks(dids);
    }
}
