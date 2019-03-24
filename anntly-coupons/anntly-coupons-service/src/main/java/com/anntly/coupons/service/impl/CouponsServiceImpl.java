package com.anntly.coupons.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.UUIDUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.coupons.dto.CouponsDto;
import com.anntly.coupons.mapper.CouponsMapper;
import com.anntly.coupons.mapper.UserCouponsMapper;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.pojo.UserCoupons;
import com.anntly.coupons.service.CouponsService;
import com.anntly.coupons.vo.CouponsParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author soledad
 * @Title: CouponsServiceImpl
 * @ProjectName anntly
 * @Description: CouponsServiceImpl
 * @date 2019/3/321:33
 */
@Service
public class CouponsServiceImpl implements CouponsService {

    @Autowired
    private CouponsMapper couponsMapper;

    @Autowired
    private UserCouponsMapper userCouponsMapper;

    @Override
    public PageResult<Coupons> queryPage(PageRequest pageRequest, CouponsParams params) {
        PageHelper.startPage(pageRequest.getPage(),pageRequest.getRows());
        List<Coupons> coupons = couponsMapper.queryPage(params);
        PageInfo<Coupons> pageInfo = new PageInfo<>(coupons);
        return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(),coupons);
    }

    @Override
    @Transactional
    public void saveCoupons(Coupons coupons) {
        coupons.setCreateTime(new Date());
        coupons.setId(UUIDUtils.getUUID());
        coupons.setDataStatus(true);
        coupons.setStatus(true);
        couponsMapper.insert(coupons);
    }

    @Override
    @Transactional
    public void updateCoupons(Coupons coupons) {
        int count = couponsMapper.updateByPrimaryKeySelective(coupons);
        if(count <= 0){
            throw new AnnException(ExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    @Transactional
    public void deleteCoupons(String id) {
        Coupons coupons = new Coupons();
        coupons.setId(id);
        coupons.setDataStatus(false);
        couponsMapper.updateByPrimaryKeySelective(coupons);
    }

    @Override
    @Transactional
    public void deleteCouponsList(List<String> ids) {
        couponsMapper.updateBatch(ids);
    }

    @Override
    @Transactional
    public void changeStatus(String id) {
        couponsMapper.changeStatus(id);
    }

    @Override
    @Transactional
    public Coupons queryCouponsById(String couponsId,Long userId) {

        // 先判断是否优惠券失效
        UserCoupons userCoupons = userCouponsMapper.selectByPrimaryKey(couponsId);
        if(userCoupons.getCouponsStatus() != 2){
            throw new AnnException(ExceptionEnum.COUPONS_EXPIRE);
        }

        // 先设置优惠券认领表中的优惠券失效
        couponsMapper.useCoupon(couponsId,userId);
        // 返回优惠券信息
        return couponsMapper.selectByPrimaryKey(couponsId);
    }

    @Override
    public List<Coupons> queryCouponsByResaurantId(Long restaurantId) {
        List<Coupons> coupons = couponsMapper.queryCouponsByResaurantId(restaurantId);
        if(CollectionUtils.isEmpty(coupons)){
            throw new AnnException(ExceptionEnum.COUPONS_NOT_FOUND);
        }
        return coupons;
    }

    @Override
    @Transactional
    public void receiveCoupon(Long id, String couponsId, Long restaurantId) {
        // 先查询是否已经存在
        if(couponsMapper.isReceive(couponsId) == 0){
            Coupons coupons = couponsMapper.selectByPrimaryKey(couponsId);

            UserCoupons userCoupons = new UserCoupons();
            userCoupons.setCouponsId(couponsId);
            userCoupons.setUserId(id);
            userCoupons.setCouponsStatus(2);
            userCoupons.setRestaurantId(restaurantId);
            userCoupons.setCreateTime(new Date());
            userCoupons.setEndTime(coupons.getEndTime());
            userCouponsMapper.insert(userCoupons);
        }

    }

    @Override
    public Map<String,List<CouponsDto>> queryMyCoupons(Long id, BigDecimal totalFee) {
        List<Coupons> coupons = userCouponsMapper.queryCouponsByUserId(id);
        List<CouponsDto> ableCoupons = new ArrayList<>();
        List<CouponsDto> disableCoupons = new ArrayList<>();
        for (Coupons coupon : coupons) {
            CouponsDto couponsDto = new CouponsDto();
            couponsDto.setId(coupon.getId());
            couponsDto.setName(coupon.getName());
            if(coupon.getType()){
                // 折扣金额
                couponsDto.setValue(coupon.getAmount().multiply(new BigDecimal("100")));
                couponsDto.setUnitDesc("元");
                couponsDto.setValueDesc(String.valueOf(coupon.getAmount()));
            }else {
                couponsDto.setValue(totalFee.multiply(new BigDecimal(coupon.getDiscount())));
                couponsDto.setUnitDesc("折");
                couponsDto.setValueDesc(String.valueOf(couponsDto.getValue().divide(new BigDecimal(100))));
            }
            couponsDto.setStartAt(coupon.getBeginTime().getTime());
            couponsDto.setEndAt(coupon.getEndTime().getTime());
            if(coupon.getNeedCost().compareTo(new BigDecimal(0)) == 0){
                couponsDto.setCondition("无门槛");
            }else {
                couponsDto.setCondition("满" + String.valueOf(coupon.getNeedCost()) + "元可使用");
            }
            // 判断满减条件
            if(coupon.getNeedCost().compareTo(totalFee) > 0){
                // 不满足条件
                couponsDto.setReason("不满足满减条件");
                disableCoupons.add(couponsDto);
            }else {
                ableCoupons.add(couponsDto);
            }
        }
        Map<String,List<CouponsDto>> result = new HashMap<>();
        result.put("coupons",ableCoupons);
        result.put("disabledCoupons",disableCoupons);
        return result;
    }
}
