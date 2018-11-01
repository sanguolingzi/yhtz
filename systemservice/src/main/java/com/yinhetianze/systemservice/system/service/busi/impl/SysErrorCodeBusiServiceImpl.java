package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.service.busi.SysErrorCodeBusiService;
import com.yinhetianze.pojo.back.SysErrorCodePojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.busi.SysErrorCodeBusiMapper;

@Service
public class SysErrorCodeBusiServiceImpl implements SysErrorCodeBusiService
{
    @Autowired
    private SysErrorCodeBusiMapper mapper;

    @Override
    public int updateByPrimaryKeySelective(SysErrorCodePojo sysErrorCodePojo){
        return mapper.updateByPrimaryKeySelective(sysErrorCodePojo);
    }

    @Override
    public int insertSelective(SysErrorCodePojo sysErrorCodePojo){
        return mapper.insertSelective(sysErrorCodePojo);
    }

    @Override
    public int deleteSelective(SysErrorCodePojo sysErrorCodePojo){
        return mapper.delete(sysErrorCodePojo);
    }
}