package com.yinhetianze.systemservice.coupon.service.busi.impl;

import com.yinhetianze.pojo.back.CouponPojo;
import com.yinhetianze.systemservice.coupon.mapper.busi.CouponbusiMapper;
import com.yinhetianze.systemservice.coupon.service.busi.CouponbusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponbusiServiceImpl implements CouponbusiService
{
    @Autowired
    private CouponbusiMapper mapper;

    @Override
    public int insertSelective(CouponPojo couponPojo) {
        return mapper.insertSelective(couponPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(CouponPojo couponPojo) {
        return mapper.updateByPrimaryKeySelective(couponPojo);
    }

    @Override
    public int updateByPrimaryKey(CouponPojo couponPojo) {
        return mapper.updateByPrimaryKey(couponPojo);
    }
}