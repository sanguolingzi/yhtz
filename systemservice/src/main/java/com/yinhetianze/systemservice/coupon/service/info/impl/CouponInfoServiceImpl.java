package com.yinhetianze.systemservice.coupon.service.info.impl;

import com.yinhetianze.pojo.back.CouponPojo;
import com.yinhetianze.systemservice.coupon.service.info.CouponInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.coupon.mapper.info.CouponInfoMapper;
import java.util.List;
import java.util.Map;

@Service
public class CouponInfoServiceImpl implements CouponInfoService
{
    @Autowired
    private CouponInfoMapper mapper;

    @Override
    public List<Map> selectCouponList(CouponPojo couponPojo) {
        return mapper.selectCouponList(couponPojo);
    }

    @Override
    public CouponPojo selectOne(CouponPojo couponPojo) {
        return mapper.selectOne(couponPojo);
    }


}