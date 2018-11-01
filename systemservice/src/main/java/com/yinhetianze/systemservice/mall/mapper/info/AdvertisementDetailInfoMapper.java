package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.AdvertisementDetailPojo;

import java.util.List;
import java.util.Map;

public interface AdvertisementDetailInfoMapper extends InfoMapper<AdvertisementDetailPojo> {

    List<Map>selectBackstageAdvertisementDetailList(AdvertisementDetailPojo advertisementDetailPojo);

    List<Map>selectAdvertisementDetailList(AdvertisementDetailPojo advertisementDetailPojo);
}