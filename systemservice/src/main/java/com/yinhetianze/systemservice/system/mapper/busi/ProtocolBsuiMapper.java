package com.yinhetianze.systemservice.system.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.back.ProtocolPojo;

public interface ProtocolBsuiMapper extends BusiMapper<ProtocolPojo> {

    int deleteProtocol(Integer[] protocolIds);
}