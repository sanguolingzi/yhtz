package com.yinhetianze.systemservice.coupon.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.CouponPojo;

import java.util.List;
import java.util.Map;

public interface CouponInfoMapper extends InfoMapper<CouponPojo> {

    List<Map> selectCouponList(CouponPojo couponPojo);

}