package com.anntly.order.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.order.dto.PayTypeDto;
import com.anntly.order.dto.ReportDto;
import com.anntly.order.dto.UserOrderDto;
import com.anntly.order.pojo.Order;
import com.anntly.order.vo.OrderParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select sum(real_pay) as money,date_format(create_time, '%Y-%m') as month from tb_order WHERE restaurant_id = #{restaurantId} AND pay_status = 1 AND `status` = 5 AND `data_status` = 1 AND `create_time` BETWEEN #{before} AND #{after} group by date_format(create_time, '%Y-%m')")
    List<ReportDto> queryReportData(@Param("restaurantId") Long restaurantId,@Param("before") String before,@Param("after") String after);

    @Select("select sum(amount) as money,date_format(create_time, '%Y-%m') as month from tb_expend WHERE `data_status` = 1 AND restaurant_id = #{restaurantId} AND `create_time` BETWEEN #{before} AND #{after} group by date_format(create_time, '%Y-%m')")
    List<ReportDto> queryReportExpend(@Param("restaurantId") Long restaurantId,@Param("before")  String before,@Param("after")  String after);

    @Select("select pay_type, count(*) as num from tb_order where pay_status = 1 and `status` = 5 and restaurant_id = #{restaurantId} GROUP BY pay_type")
    List<PayTypeDto> queryReportPayType(@Param("restaurantId") Long restaurantId);

    List<UserOrderDto> queryUserOrders(@Param("username") String username,@Param("status") Integer status,@Param("type") Boolean type,@Param("payStatus") Boolean payStatus);
}
