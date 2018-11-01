package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.DetonatingPojo;

import java.util.List;
import java.util.Map;

public interface DetonatingInfoMapper extends InfoMapper<DetonatingPojo> {
    List<Map>selectBackstageDetonatingList(DetonatingPojo detonatingPojo);
    List<Map>selectDetonatingList();
}