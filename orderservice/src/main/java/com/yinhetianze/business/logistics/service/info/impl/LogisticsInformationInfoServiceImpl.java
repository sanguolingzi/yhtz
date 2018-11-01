package com.yinhetianze.business.logistics.service.info.impl;


import com.yinhetianze.business.logistics.service.info.LogisticsInformationInfoService;
import com.yinhetianze.pojo.order.LogisticsInformationPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.logistics.mapper.LogisticsInformationInfoMapper;

import java.util.Map;

@Service
public class LogisticsInformationInfoServiceImpl implements LogisticsInformationInfoService
{
    @Autowired
    private LogisticsInformationInfoMapper mapper;

    @Override
    public LogisticsInformationPojo getLogisticsInformation(LogisticsInformationPojo logisticsInformationPojo){

        return mapper.selectOne(logisticsInformationPojo);
    }

    @Override
    public Map getLogistics(LogisticsInformationPojo logisticsPojo){
        return mapper.getLogistics(logisticsPojo);
    }
}