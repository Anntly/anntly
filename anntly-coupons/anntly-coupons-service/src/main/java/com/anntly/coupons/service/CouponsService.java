package com.anntly.coupons.service;

import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.vo.CouponsParams;

import java.util.List;

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
}
