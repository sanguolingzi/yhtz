package com.yinhetianze.systemservice.coupon.service.info;

import com.yinhetianze.pojo.back.CouponPojo;
import java.util.List;
import java.util.Map;

public interface CouponInfoService
{
    List<Map> selectCouponList(CouponPojo couponPojo);
    CouponPojo selectOne(CouponPojo couponPojo);

}