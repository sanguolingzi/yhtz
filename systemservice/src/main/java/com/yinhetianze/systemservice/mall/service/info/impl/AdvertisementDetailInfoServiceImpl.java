package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.pojo.back.AdvertisementDetailPojo;
import com.yinhetianze.systemservice.mall.service.info.AdvertisementDetailInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.mall.mapper.info.AdvertisementDetailInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class AdvertisementDetailInfoServiceImpl implements AdvertisementDetailInfoService
{
    @Autowired
    private AdvertisementDetailInfoMapper mapper;

    @Override
    public List<Map> selectBackstageAdvertisementDetailList(AdvertisementDetailPojo advertisementDetailPojo) {
        return mapper.selectBackstageAdvertisementDetailList(advertisementDetailPojo);
    }

    @Override
    public AdvertisementDetailPojo selectOne(AdvertisementDetailPojo advertisementDetailPojo) {
        return mapper.selectOne(advertisementDetailPojo);
    }

    @Override
    public List<Map> selectAdvertisementDetailList(AdvertisementDetailPojo advertisementDetailPojo) {
        return mapper.selectAdvertisementDetailList(advertisementDetailPojo);
    }
}