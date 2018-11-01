package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.systemservice.mall.model.FloorMobileIndexModel;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;
import com.yinhetianze.pojo.back.SysFloorPojo;

import java.util.List;
import java.util.Map;

public interface SysFloorInfoService
{
    List selectList(SysFloorPojo sysFloorPojo);

    List<SysFloorModel> selectSysFloorList(SysFloorModel sysFloorModel);

    List selectForMobile(SysFloorPojo sysFloorPojo);

    SysFloorPojo selectOne(SysFloorPojo sysFloorPojo);

    FloorMobileIndexModel selectFloorOne(SysFloorPojo sysFloorPojo);

    List<SysFloorPojo> selectFloorList();

    List<Map>getFloorList();
}