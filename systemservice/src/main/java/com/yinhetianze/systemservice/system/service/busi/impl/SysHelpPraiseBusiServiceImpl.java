package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.pojo.back.SysHelpPraisePojo;
import com.yinhetianze.systemservice.system.service.busi.SysHelpPraiseBusiService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.busi.SysHelpPraiseBusiMapper;

@Service
public class SysHelpPraiseBusiServiceImpl implements SysHelpPraiseBusiService
{
    @Autowired
    private SysHelpPraiseBusiMapper mapper;

    @Override
    public int insertSelective(SysHelpPraisePojo sysHelpPraisePojo) {
        return mapper.insertSelective(sysHelpPraisePojo);
    }

}