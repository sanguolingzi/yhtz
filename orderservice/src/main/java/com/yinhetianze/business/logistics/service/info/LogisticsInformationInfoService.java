package com.yinhetianze.business.logistics.service.info;

import com.yinhetianze.pojo.order.LogisticsInformationPojo;

import java.util.Map;

public interface LogisticsInformationInfoService
{
    LogisticsInformationPojo getLogisticsInformation(LogisticsInformationPojo logisticsInformationPojo);

    Map getLogistics(LogisticsInformationPojo logisticsPojo);
}