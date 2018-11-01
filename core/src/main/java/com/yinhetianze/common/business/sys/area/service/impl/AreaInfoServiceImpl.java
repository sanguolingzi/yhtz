package com.yinhetianze.common.business.sys.area.service.impl;

import com.yinhetianze.common.business.sys.area.mapper.AreaInfoMapper;
import com.yinhetianze.common.business.sys.area.service.AreaInfoService;
import com.yinhetianze.pojo.task.AreaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaInfoServiceImpl implements AreaInfoService
{
    @Autowired
    private AreaInfoMapper mapper;

    @Override
    public List<AreaPojo> getAreaList(AreaPojo pojo)
    {
        return mapper.select(pojo);
    }

    @Override
    public AreaPojo findArea(AreaPojo pojo)
    {
        return mapper.selectOne(pojo);
    }

    @Override
    public List<Map<String, Object>> getAreaMapList(Map<String, Object> params)
    {
        return mapper.getAreaMapList(params);
    }
}