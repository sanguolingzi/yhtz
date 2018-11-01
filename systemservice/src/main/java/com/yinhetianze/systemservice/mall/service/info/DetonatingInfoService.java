package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.pojo.back.DetonatingPojo;

import java.util.List;
import java.util.Map;

public interface DetonatingInfoService
{
    List<Map> selectBackstageDetonatingList(DetonatingPojo detonatingPojo);

    DetonatingPojo selectOne(DetonatingPojo detonatingPojo);

    List<Map>selectDetonatingList();
}