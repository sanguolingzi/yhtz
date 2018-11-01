package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.systemservice.mall.model.FloorDetailProductPageModel;
import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.info.SysFloorDetailInfoService;
import com.yinhetianze.pojo.back.SysFloorDetailPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.mall.mapper.info.SysFloorDetailInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class SysFloorDetailInfoServiceImpl implements SysFloorDetailInfoService
{
    @Autowired
    private SysFloorDetailInfoMapper mapper;

    @Override
    public List<SysFloorDetailModel> selectSysFloorProductList(SysFloorDetailModel sysFloorDetailModel) {
        return mapper.selectSysFloorProductList(sysFloorDetailModel);
    }

    @Override
    public List<Map> selectForMobileIndex(SysFloorDetailModel sysFloorDetailModel) {
        return mapper.selectForMobileIndex(sysFloorDetailModel);
    }

    @Override
    public SysFloorDetailPojo selectOne(SysFloorDetailPojo sysFloorDetailPojo) {
        sysFloorDetailPojo.setDelFlag((short)0);
        return mapper.selectOne(sysFloorDetailPojo);
    }

    @Override
    public List<Map> selectPcFloorProductList(FloorDetailProductPageModel floorDetailProductPageModel) {
        return mapper.selectPcFloorProductList(floorDetailProductPageModel);
    }

    @Override
    public List<Map> selectForPcIndex(SysFloorDetailModel sysFloorDetailModel) {
        return mapper.selectForPcIndex(sysFloorDetailModel);
    }
}