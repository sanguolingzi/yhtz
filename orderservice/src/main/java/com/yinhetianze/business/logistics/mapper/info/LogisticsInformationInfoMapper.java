package com.yinhetianze.business.logistics.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.order.LogisticsInformationPojo;

import java.util.Map;

public interface LogisticsInformationInfoMapper extends InfoMapper<LogisticsInformationPojo> {
    Map getLogistics(LogisticsInformationPojo logisticsPojo);
}