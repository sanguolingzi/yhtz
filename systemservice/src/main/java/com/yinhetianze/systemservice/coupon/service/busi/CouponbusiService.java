package com.yinhetianze.systemservice.coupon.service.busi;

import com.yinhetianze.pojo.back.CouponPojo;

public interface CouponbusiService
{
    int insertSelective(CouponPojo couponPojo);
    int updateByPrimaryKeySelective(CouponPojo couponPojo);
    int updateByPrimaryKey(CouponPojo couponPojo);
}