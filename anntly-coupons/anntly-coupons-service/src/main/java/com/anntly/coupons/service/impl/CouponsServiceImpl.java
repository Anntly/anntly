package com.anntly.coupons.service.impl;

import com.anntly.common.enums.ExceptionEnum;
import com.anntly.common.exception.AnnException;
import com.anntly.common.utils.UUIDUtils;
import com.anntly.common.vo.PageRequest;
import com.anntly.common.vo.PageResult;
import com.anntly.coupons.mapper.CouponsMapper;
import com.anntly.coupons.pojo.Coupons;
import com.anntly.coupons.service.CouponsService;
import com.anntly.coupons.vo.CouponsParams;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    public Coupons queryCouponsById(String id) {
        // 先扣库存
        int count = couponsMapper.reduceNum(id);
        if(count <= 0){
            return null;
        }
        // 返回优惠券信息
        return couponsMapper.selectByPrimaryKey(id);
    }
}
