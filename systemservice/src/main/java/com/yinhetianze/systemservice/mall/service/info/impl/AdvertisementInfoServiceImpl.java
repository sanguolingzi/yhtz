package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.pojo.back.AdvertisementPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementIndexModel;
import com.yinhetianze.systemservice.mall.model.AdvertisementModel;
import com.yinhetianze.systemservice.mall.service.info.AdvertisementInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.mall.mapper.info.AdvertisementInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class AdvertisementInfoServiceImpl implements AdvertisementInfoService
{
    @Autowired
    private AdvertisementInfoMapper mapper;

    @Override
    public List<Map> selectBackstageAdvertisementList(AdvertisementPojo advertisementPojo) {
        return mapper.selectBackstageAdvertisementList(advertisementPojo);
    }

    @Override
    public AdvertisementPojo selectOne(AdvertisementPojo advertisementPojo) {
        return mapper.selectOne(advertisementPojo);
    }

    @Override
    public List<AdvertisementIndexModel> selectAdvertisementList(AdvertisementModel advertisementModel) {
        return mapper.selectAdvertisementList(advertisementModel);
    }
}