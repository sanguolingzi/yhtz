package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.systemservice.mall.model.FloorMobileIndexModel;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.SysFloorPojo;

import java.util.List;
import java.util.Map;


public interface SysFloorInfoMapper extends InfoMapper<SysFloorPojo> {
    List<SysFloorModel> selectSysFloorList(SysFloorModel sysFloorModel);

    FloorMobileIndexModel selectFloorOne(SysFloorPojo sysFloorPojo);

    List<SysFloorPojo> selectFloorList();

    List<Map>getFloorList();
}