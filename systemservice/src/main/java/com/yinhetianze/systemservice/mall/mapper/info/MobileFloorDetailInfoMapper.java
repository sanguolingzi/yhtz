package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.MobileFloorDetailPojo;

import java.util.List;
import java.util.Map;

public interface MobileFloorDetailInfoMapper extends InfoMapper<MobileFloorDetailPojo> {

    List<Map>selectMobileFloorDetailList(MobileFloorDetailPojo mobileFloorDetailPojo);

    List<Map>selectMobileFloorIndex(MobileFloorDetailPojo mobileFloorDetailPojo);



}