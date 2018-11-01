package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.pojo.back.AdvertisementDetailPojo;

import java.util.List;
import java.util.Map;

public interface AdvertisementDetailInfoService
{
    List<Map> selectBackstageAdvertisementDetailList(AdvertisementDetailPojo advertisementDetailPojo);
    AdvertisementDetailPojo selectOne(AdvertisementDetailPojo advertisementDetailPojo);
    List<Map>selectAdvertisementDetailList(AdvertisementDetailPojo advertisementDetailPojo);
}