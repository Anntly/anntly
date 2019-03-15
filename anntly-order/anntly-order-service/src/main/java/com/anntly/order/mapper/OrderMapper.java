package com.anntly.order.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.order.pojo.Order;
import com.anntly.order.vo.OrderParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author soledad
 * @Title: OrderMapper
 * @ProjectName anntly
 * @Description: OrderMapper
 * @date 2019/3/710:06
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> queryPage(@Param("params") OrderParams params);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<Long> ids);
}
