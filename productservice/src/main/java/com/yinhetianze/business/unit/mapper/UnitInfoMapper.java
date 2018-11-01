package com.yinhetianze.business.unit.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.unit.UnitPojo;

import java.util.List;
import java.util.Map;

public interface UnitInfoMapper extends InfoMapper<UnitPojo> {

    List<UnitPojo> getUnitList(Map<String, Object> param);

}