package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.mapper.info.SysBannerInfoMapper;
import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.systemservice.system.service.info.SysBannerInfoService;
import com.yinhetianze.pojo.back.SysBannerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysBannerInfoServiceImpl implements SysBannerInfoService
{
    @Autowired
    private SysBannerInfoMapper mapper;


    @Override
    public List selectList(SysBannerPojo sysBannerPojo){
        return mapper.select(sysBannerPojo);
    }

    @Override
    public List<SysBannerModel> selectSysBannerList(SysBannerModel sysBannerModel) {
        return mapper.selectSysBannerList(sysBannerModel);
    }

    @Override
    public SysBannerPojo selectOne(SysBannerPojo sysBannerPojo) {
        sysBannerPojo.setDelFlag((short)0);
        return mapper.selectOne(sysBannerPojo);
    }

    @Override
    public List<Map> selectForMobileIndex(SysBannerModel sysBannerModel) {
        return mapper.selectForMobileIndex(sysBannerModel);
    }
}