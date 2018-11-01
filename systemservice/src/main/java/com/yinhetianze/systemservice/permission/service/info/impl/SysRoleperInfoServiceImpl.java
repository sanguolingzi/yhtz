package com.yinhetianze.systemservice.permission.service.info.impl;

import com.yinhetianze.pojo.back.BusiSysRoleperPojo;
import com.yinhetianze.systemservice.permission.service.info.SysRoleperInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.permission.mapper.info.SysRoleperInfoMapper;

import java.util.List;

@Service
public class SysRoleperInfoServiceImpl implements SysRoleperInfoService
{
    @Autowired
    private SysRoleperInfoMapper sysRoleperInfoMapper;

    @Override
    public List<BusiSysRoleperPojo> select(BusiSysRoleperPojo busiSysRoleperPojo) {
        busiSysRoleperPojo.setDelFlag((short)0);
        return sysRoleperInfoMapper.select(busiSysRoleperPojo);
    }
}