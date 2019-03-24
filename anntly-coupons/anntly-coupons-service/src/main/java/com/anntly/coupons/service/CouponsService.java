package com.anntly.coupons.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.coupons.dto.CouponsDto;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.vo.CouponsParams;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author soledad
 * @Title: CouponsService
 * @ProjectName anntly
 * @Description: CouponsService
 * @date 2019/3/321:28
 */
public interface CouponsService {
    PageResult<Coupons> queryPage(PageRequest pageRequest, CouponsParams params);

    void saveCoupons(Coupons coupons);

    void updateCoupons(Coupons coupons);

    void deleteCoupons(String id);

    void deleteCouponsList(List<String> ids);

    void changeStatus(String id);

    Coupons queryCouponsById(String couponsId,Long userId);

    List<Coupons> queryCouponsByResaurantId(Long restaurantId);

    void receiveCoupon(Long id, String couponsId, Long restaurantId);

    Map<String,List<CouponsDto>> queryMyCoupons(Long id, BigDecimal totalFee);
}
