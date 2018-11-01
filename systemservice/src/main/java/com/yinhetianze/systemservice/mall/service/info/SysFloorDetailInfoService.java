package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.systemservice.mall.model.FloorDetailProductPageModel;
import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.pojo.back.SysFloorDetailPojo;

import java.util.List;
import java.util.Map;

public interface SysFloorDetailInfoService
{
    List<SysFloorDetailModel> selectSysFloorProductList(SysFloorDetailModel sysFloorDetailModel);

    List<Map> selectForMobileIndex(SysFloorDetailModel sysFloorDetailModel);

    List<Map> selectForPcIndex(SysFloorDetailModel sysFloorDetailModel);

    SysFloorDetailPojo selectOne(SysFloorDetailPojo sysFloorDetailPojo);

    List<Map> selectPcFloorProductList(FloorDetailProductPageModel floorDetailProductPageModel);
}