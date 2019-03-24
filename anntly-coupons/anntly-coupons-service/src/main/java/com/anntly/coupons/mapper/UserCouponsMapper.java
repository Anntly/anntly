package com.anntly.coupons.mapper;

import com.anntly.common.mapper.BaseMapper;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.pojo.UserCoupons;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author soledad
 * @Title: UserCouponsMapper
 * @ProjectName anntly
 * @Description:
 * @date 2019/3/2415:18
 */
public interface UserCouponsMapper extends BaseMapper<UserCoupons> {

    @Select("select c.* from tb_coupons c, tb_user_coupons uc where uc.user_id = #{userId} and uc.coupons_id = c.id")
    List<Coupons> queryCouponsByUserId(@Param("userId") Long userId);
}
