package com.anntly.shop.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.shop.dto.Node;
import com.anntly.shop.pojo.Desk;
import com.anntly.shop.vo.DeskParams;

import java.util.List;

/**
 * @author soledad
 * @Title: DeskService
 * @ProjectName anntly
 * @Description: DeskService
 * @date 2019/3/121:48
 */
public interface DeskService {
    PageResult<Desk> queryPage(PageRequest pageRequest, DeskParams params);

    void saveDesk(Desk desk);

    void updateDesk(Desk desk);

    void deleteDesk(Long id);

    void deleteDesks(List<Long> ids);

    List<Long> queryDesksByRoomId(Long roomId);

    List<Long> queryDesksByRoomIds(List<Long> mids);

    List<Node> queryDeskNodesByRid(Long restaurantId);
}
