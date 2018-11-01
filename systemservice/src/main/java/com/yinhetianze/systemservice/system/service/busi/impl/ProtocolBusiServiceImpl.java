package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.service.busi.ProtocolBusiService;
import com.yinhetianze.pojo.back.ProtocolPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.busi.ProtocolBsuiMapper;

@Service
public class ProtocolBusiServiceImpl implements ProtocolBusiService
{
    @Autowired
    private ProtocolBsuiMapper mapper;


    @Override
    public int insertSelective(ProtocolPojo protocolPojo) {
        return mapper.insertSelective(protocolPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ProtocolPojo protocolPojo) {
        return mapper.updateByPrimaryKeySelective(protocolPojo);
    }

    @Override
    public int deleteProtocol(Integer[] protocolIds) {
        return mapper.deleteProtocol(protocolIds);
    }
}