package com.anntly.shop.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.mapper.DeskMapper;
import com.anntly.shop.pojo.Desk;
import com.anntly.shop.service.DeskService;
import com.anntly.shop.vo.DeskParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author soledad
 * @Title: DeskServiceImpl
 * @ProjectName anntly
 * @Description: DeskServiceImpl
 * @date 2019/3/121:48
 */
@Service
public class DeskServiceImpl implements DeskService {

    @Autowired
    private DeskMapper deskMapper;

    @Override
    public PageResult<Desk> queryPage(PageRequest pageRequest, DeskParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<Desk> desks = deskMapper.queryPage(params);
        PageInfo<Desk> pageInfo = new PageInfo<>(desks);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),desks);
    }

    @Override
    @Transactional
    public void saveDesk(Desk desk) {
        desk.setCreateTime(new Date());
        desk.setUpdateTime(desk.getCreateTime());
        deskMapper.insert(desk);
    }

    @Override
    @Transactional
    public void updateDesk(Desk desk) {
        desk.setUpdateTime(new Date());
        int count = deskMapper.updateByPrimaryKeySelective(desk);
        if(count<=0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void deleteDesk(Long id) {
        deskMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void deleteDesks(List<Long> ids) {
        deskMapper.deleteByIdList(ids);
    }

    @Override
    public List<Long> queryDesksByRoomId(Long roomId) {
        return deskMapper.queryDesksByRoomId(roomId);
    }

    @Override
    public List<Long> queryDesksByRoomIds(List<Long> mids) {
        List<Long> ids = deskMapper.queryDeskIdsByMid(mids);
        if(CollectionUtils.isEmpty(ids)){
            throw new AnnException(ExceptionEnum.Desks_NOT_FOUND);
        }
        return ids;
    }

    @Override
    public List<Node> queryDeskNodesByRid(Long restaurantId) {
        List<Node> nodes = deskMapper.queryDeskNodesByRid(restaurantId);
        if(CollectionUtils.isEmpty(nodes)){
            throw new AnnException(ExceptionEnum.Desks_NOT_FOUND);
        }
        return nodes;
    }
}
