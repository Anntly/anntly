package com.anntly.order.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.order.dto.FoodReport;
import com.anntly.order.pojo.OrderDetail;
import com.anntly.order.vo.OrderDetailParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author soledad
 * @Title: OrderDetailMapper
 * @ProjectName anntly
 * @Description: OrderDetailMapper
 * @date 2019/3/715:39
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {


    List<OrderDetail> queryPage(@Param("params") OrderDetailParams params);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<Long> ids);

    @Select("select * from tb_order_detail where order_id = #{orderId}")
    List<OrderDetail> queryOrderDetailsByOrderId(@Param("orderId") Long id);

    @Select("SELECT `name`, count(*) as num FROM tb_order_detail WHERE data_status = 1 AND order_id in (SELECT order_id FROM tb_order WHERE restaurant_id = #{restaurantId} AND pay_status = 1 AND `status` = 5) GROUP BY `name` ORDER BY num desc LIMIT 10")
    List<FoodReport> queryFoodReport(@Param("restaurantId") Long restaurantId);
}
