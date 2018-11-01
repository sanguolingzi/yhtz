package com.yinhetianze.business.unit.service.impl;

import com.yinhetianze.business.unit.service.UnitInfoService;
import com.yinhetianze.pojo.unit.UnitPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.unit.mapper.UnitInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class UnitInfoServiceImpl implements UnitInfoService
{
    @Autowired
    private UnitInfoMapper mapper;

    @Override
    public List<UnitPojo> getUnitList(Map<String, Object> params)
    {
        return mapper.getUnitList(params);
    }

    @Override
    public UnitPojo findUnit(UnitPojo pojo)
    {
        return mapper.selectOne(pojo);
    }
}