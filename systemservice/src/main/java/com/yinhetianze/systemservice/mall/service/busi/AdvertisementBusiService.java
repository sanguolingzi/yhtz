package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.pojo.back.AdvertisementPojo;

public interface AdvertisementBusiService
{
    int addAdvertisement(AdvertisementPojo advertisementPojo);

    int updateByPrimaryKeySelective(AdvertisementPojo advertisementPojo);
}