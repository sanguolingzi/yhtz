package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.AdvertisementPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementIndexModel;
import com.yinhetianze.systemservice.mall.model.AdvertisementModel;

import java.util.List;
import java.util.Map;

public interface AdvertisementInfoMapper extends InfoMapper<AdvertisementPojo> {

    List<Map> selectBackstageAdvertisementList(AdvertisementPojo advertisementPojo);

    List<AdvertisementIndexModel>selectAdvertisementList(AdvertisementModel advertisementModel);

}