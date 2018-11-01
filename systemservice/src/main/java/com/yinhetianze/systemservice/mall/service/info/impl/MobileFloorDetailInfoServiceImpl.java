package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.pojo.back.MobileFloorDetailPojo;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorDetailInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.mall.mapper.info.MobileFloorDetailInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class MobileFloorDetailInfoServiceImpl implements MobileFloorDetailInfoService
{
    @Autowired
    private MobileFloorDetailInfoMapper mapper;

    @Override
    public List<Map> selectMobileFloorDetailList(MobileFloorDetailPojo mobileFloorDetailPojo) {
        return mapper.selectMobileFloorDetailList(mobileFloorDetailPojo);
    }

    @Override
    public MobileFloorDetailPojo selectOne(MobileFloorDetailPojo mobileFloorDetailPojo) {
        return mapper.selectOne(mobileFloorDetailPojo);
    }

    @Override
    public List<Map> selectMobileFloorIndex(MobileFloorDetailPojo mobileFloorDetailPojo) {
        return mapper.selectMobileFloorIndex(mobileFloorDetailPojo);
    }
}