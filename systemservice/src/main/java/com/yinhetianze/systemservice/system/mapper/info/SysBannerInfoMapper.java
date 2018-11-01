package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.SysBannerPojo;

import java.util.List;
import java.util.Map;


public interface SysBannerInfoMapper extends InfoMapper<SysBannerPojo> {
    List<SysBannerModel> selectSysBannerList(SysBannerModel sysBannerModel);
    List<Map> selectForMobileIndex(SysBannerModel sysBannerModel);
}