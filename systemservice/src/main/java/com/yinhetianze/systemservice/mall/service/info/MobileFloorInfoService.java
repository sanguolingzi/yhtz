package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.pojo.back.MobileFloorPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorIndexModel;
import com.yinhetianze.systemservice.mall.model.MobileFloorModel;

import java.util.List;
import java.util.Map;

public interface MobileFloorInfoService
{
    List<Map> selectMobileFloorList(MobileFloorPojo mobileFloorPojo);

    MobileFloorPojo selectOne(MobileFloorPojo mobileFloorPojo);

    List<MobileFloorPojo> selectList(MobileFloorPojo mobileFloorPojo);

    MobileFloorIndexModel selectMobileFloorOne(MobileFloorPojo mobileFloorPojo);
}