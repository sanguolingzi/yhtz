package com.yinhetianze.business.unit.service;

import com.yinhetianze.pojo.unit.UnitPojo;

import java.util.List;
import java.util.Map;

public interface UnitInfoService
{
    List<UnitPojo> getUnitList(Map<String, Object> params);

    UnitPojo findUnit(UnitPojo pojo);
}