package com.yinhetianze.business.logistics.service.busi;

import com.yinhetianze.pojo.order.LogisticsInformationPojo;

public interface LogisticsInformationBusiService
{
    int logisticsInformation(LogisticsInformationPojo logisticsInformationPojo);

    int updateLogisticsInformation(LogisticsInformationPojo pojo);
}