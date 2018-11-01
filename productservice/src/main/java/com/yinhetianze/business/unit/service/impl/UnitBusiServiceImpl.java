package com.yinhetianze.business.unit.service.impl;

import com.yinhetianze.business.unit.service.UnitBusiService;
import com.yinhetianze.pojo.unit.UnitPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.unit.mapper.UnitBusiMapper;

@Service
public class UnitBusiServiceImpl implements UnitBusiService
{
    @Autowired
    private UnitBusiMapper mapper;

    @Override
    public int addUnit(UnitPojo pojo)
    {
        return mapper.insert(pojo);
    }

    @Override
    public int modifyUnit(UnitPojo pojo)
    {
        return mapper.updateByPrimaryKey(pojo);
    }

    @Override
    public int deleteUnit(UnitPojo pojo)
    {
        return mapper.deleteByPrimaryKey(pojo);
    }
}