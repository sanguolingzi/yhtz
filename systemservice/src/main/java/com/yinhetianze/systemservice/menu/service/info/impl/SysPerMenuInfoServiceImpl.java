package com.yinhetianze.systemservice.menu.service.info.impl;

import com.yinhetianze.systemservice.menu.mapper.info.SysPerMenuInfoMapper;
import com.yinhetianze.pojo.back.BusiSysPerMenuPojo;
import com.yinhetianze.systemservice.menu.service.info.SysPerMenuInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SysPerMenuInfoServiceImpl implements SysPerMenuInfoService
{
    @Autowired
    private SysPerMenuInfoMapper sysPerMenuInfoMapper;

    @Override
    public List<BusiSysPerMenuPojo> selectList(BusiSysPerMenuPojo busiSysPerMenuPojo) {
        busiSysPerMenuPojo.setDelFlag((short)0);
        return sysPerMenuInfoMapper.select(busiSysPerMenuPojo);
    }
}