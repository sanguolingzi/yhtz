package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.systemservice.mall.model.FloorDetailProductPageModel;
import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.SysFloorDetailPojo;
import java.util.List;
import java.util.Map;

public interface SysFloorDetailInfoMapper extends InfoMapper<SysFloorDetailPojo> {
    List<SysFloorDetailModel> selectSysFloorProductList(SysFloorDetailModel sysFloorDetailModel);
    List<Map> selectForMobileIndex(SysFloorDetailModel sysFloorDetailModel);
    List<Map> selectForPcIndex(SysFloorDetailModel sysFloorDetailModel);
    List<Map> selectPcFloorProductList(FloorDetailProductPageModel floorDetailProductPageModel);
}