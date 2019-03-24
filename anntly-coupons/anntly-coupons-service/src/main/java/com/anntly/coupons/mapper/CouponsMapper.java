package com.anntly.coupons.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.vo.CouponsParams;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author soledad
 * @Title: CouponsMapper
 * @ProjectName anntly
 * @Description: CouponsMapper
 * @date 2019/3/321:35
 */
public interface CouponsMapper extends BaseMapper<Coupons> {

    List<Coupons> queryPage(@Param("params") CouponsParams params);

    /**
     * 根据id批量删除节点(更新data_status为0)
     * @param ids
     */
    int updateBatch(@Param("list") List<String> ids);

    @Update("update tb_coupons set status = not status where id = #{id}")
    void changeStatus(@Param("id") String id);

    @Update("update tb_user_coupons set coupons_status = 0 where coupons_id = #{couponsId} and user_id = #{userId}")
    void useCoupon(@Param("couponsId") String couponsId,@Param("userId") Long userId);

    @Select("select * from tb_coupons where restaurant_id = #{restaurantId} and data_status = 1 and `status` = 1")
    List<Coupons> queryCouponsByResaurantId(@Param("restaurantId") Long restaurantId);

    @Select("select count(*) from tb_user_coupons where coupons_id = #{couponsId}")
    int isReceive(@Param("couponsId") String couponsId);
}
