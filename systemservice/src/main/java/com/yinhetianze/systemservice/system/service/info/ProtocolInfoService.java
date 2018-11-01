package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.pojo.back.ProtocolPojo;

import java.util.List;

public interface ProtocolInfoService
{
    List selectList(ProtocolPojo protocolPojo);
}