package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.pojo.back.MobileFloorPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorIndexModel;
import com.yinhetianze.systemservice.mall.model.MobileFloorModel;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.mall.mapper.info.MobileFloorInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class MobileFloorInfoServiceImpl implements MobileFloorInfoService
{
    @Autowired
    private MobileFloorInfoMapper mapper;

    @Override
    public List<Map> selectMobileFloorList(MobileFloorPojo mobileFloorPojo) {
        return mapper.selectMobileFloorList(mobileFloorPojo);
    }

    @Override
    public MobileFloorPojo selectOne(MobileFloorPojo mobileFloorPojo) {
        return mapper.selectOne(mobileFloorPojo);
    }

    @Override
    public List<MobileFloorPojo> selectList(MobileFloorPojo mobileFloorPojo) {
        return mapper.select(mobileFloorPojo);
    }

    @Override
    public MobileFloorIndexModel selectMobileFloorOne(MobileFloorPojo mobileFloorPojo) {
        return mapper.selectMobileFloorOne(mobileFloorPojo);
    }
}