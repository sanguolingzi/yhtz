package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.pojo.back.MobileFloorDetailPojo;

import java.util.List;
import java.util.Map;

public interface MobileFloorDetailInfoService
{
    List<Map> selectMobileFloorDetailList(MobileFloorDetailPojo mobileFloorDetailPojo);

    MobileFloorDetailPojo selectOne(MobileFloorDetailPojo mobileFloorDetailPojo);

    List<Map>selectMobileFloorIndex(MobileFloorDetailPojo mobileFloorDetailPojo);
}