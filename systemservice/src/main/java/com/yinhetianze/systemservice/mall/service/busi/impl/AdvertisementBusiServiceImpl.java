package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.pojo.back.AdvertisementPojo;
import com.yinhetianze.systemservice.mall.mapper.busi.AdvertisementBusiMapper;
import com.yinhetianze.systemservice.mall.service.busi.AdvertisementBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementBusiServiceImpl implements AdvertisementBusiService
{
    @Autowired
    private AdvertisementBusiMapper mapper;

    @Override
    public int addAdvertisement(AdvertisementPojo advertisementPojo) {
        return mapper.insertSelective(advertisementPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(AdvertisementPojo advertisementPojo) {
        return mapper.updateByPrimaryKeySelective(advertisementPojo);
    }
}