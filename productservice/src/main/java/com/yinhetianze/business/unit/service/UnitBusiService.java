package com.yinhetianze.business.unit.service;

import com.yinhetianze.pojo.unit.UnitPojo;

public interface UnitBusiService
{
    int addUnit(UnitPojo pojo);

    int modifyUnit(UnitPojo pojo);

    int deleteUnit(UnitPojo pojo);
}