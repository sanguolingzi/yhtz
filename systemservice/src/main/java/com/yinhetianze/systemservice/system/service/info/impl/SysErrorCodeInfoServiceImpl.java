package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.service.info.SysErrorCodeInfoService;
import com.yinhetianze.pojo.back.SysErrorCodePojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.info.SysErrorCodeInfoMapper;

import java.util.List;

@Service
public class SysErrorCodeInfoServiceImpl implements SysErrorCodeInfoService
{
    @Autowired
    private SysErrorCodeInfoMapper mapper;

    @Override
    public List selectList(SysErrorCodePojo sysErrorCodePojo){
        return mapper.select(sysErrorCodePojo);
    }
}