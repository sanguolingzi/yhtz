package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.MobileFloorPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorIndexModel;


import java.util.List;
import java.util.Map;

public interface MobileFloorInfoMapper extends InfoMapper<MobileFloorPojo> {

    List<Map> selectMobileFloorList(MobileFloorPojo mobileFloorPojo);
    MobileFloorIndexModel selectMobileFloorOne(MobileFloorPojo mobileFloorPojo);

}