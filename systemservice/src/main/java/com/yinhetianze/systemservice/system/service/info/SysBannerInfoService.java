package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.pojo.back.SysBannerPojo;

import java.util.List;
import java.util.Map;

public interface SysBannerInfoService
{

    List selectList(SysBannerPojo sysBannerPojo);

    List<SysBannerModel> selectSysBannerList(SysBannerModel sysBannerModel);

    List<Map> selectForMobileIndex(SysBannerModel sysBannerModel);

    SysBannerPojo selectOne(SysBannerPojo sysBannerPojo);
}