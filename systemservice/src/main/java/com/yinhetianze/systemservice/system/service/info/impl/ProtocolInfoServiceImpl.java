package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.service.info.ProtocolInfoService;
import com.yinhetianze.pojo.back.ProtocolPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.info.ProtocolInfoMapper;

import java.util.List;

@Service
public class ProtocolInfoServiceImpl implements ProtocolInfoService
{
    @Autowired
    private ProtocolInfoMapper mapper;

    @Override
    public List selectList(ProtocolPojo protocolPojo) {
        return mapper.select(protocolPojo);
    }
}