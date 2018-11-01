package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.pojo.back.ProtocolPojo;

public interface ProtocolBusiService
{
    int insertSelective(ProtocolPojo searchBoxPojo);

    int updateByPrimaryKeySelective(ProtocolPojo searchBoxPojo);

    int deleteProtocol(Integer[] protocolIds);

}