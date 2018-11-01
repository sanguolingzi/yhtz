package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.pojo.back.AdvertisementPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementIndexModel;
import com.yinhetianze.systemservice.mall.model.AdvertisementModel;

import java.util.List;
import java.util.Map;

public interface AdvertisementInfoService
{
    List<Map> selectBackstageAdvertisementList(AdvertisementPojo advertisementPojo);
    AdvertisementPojo selectOne(AdvertisementPojo advertisementPojo);
    List<AdvertisementIndexModel>selectAdvertisementList(AdvertisementModel advertisementModel);
}