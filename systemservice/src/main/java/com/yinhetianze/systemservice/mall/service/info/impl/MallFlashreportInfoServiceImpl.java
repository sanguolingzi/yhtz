package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.systemservice.mall.mapper.info.MallFlashreportInfoMapper;
import com.yinhetianze.systemservice.mall.model.MallFlashreportModel;
import com.yinhetianze.systemservice.mall.service.info.MallFlashreportInfoService;
import com.yinhetianze.pojo.back.BusiMallFlashreportPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class MallFlashreportInfoServiceImpl implements MallFlashreportInfoService {

    @Autowired
    private MallFlashreportInfoMapper mapper;

    @Override
    public List<MallFlashreportModel> getBusiMallFlashreport(MallFlashreportModel mallFlashreportModel){
        return mapper.getBusiMallFlashreport(mallFlashreportModel);
    }

    @Override
    public BusiMallFlashreportPojo selectOne(BusiMallFlashreportPojo busiMallFlashreportPojo) {
        busiMallFlashreportPojo.setDelFlag((short)0);
        return mapper.selectOne(busiMallFlashreportPojo);
    }
}