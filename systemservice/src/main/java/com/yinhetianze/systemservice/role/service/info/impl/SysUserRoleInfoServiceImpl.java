package com.yinhetianze.systemservice.role.service.info.impl;

import com.yinhetianze.systemservice.role.mapper.info.SysUserRoleInfoMapper;
import com.yinhetianze.pojo.back.BusiSysUserRolePojo;
import com.yinhetianze.systemservice.role.service.info.SysUserRoleInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SysUserRoleInfoServiceImpl implements SysUserRoleInfoService
{
    @Autowired
    private SysUserRoleInfoMapper sysUserRoleInfoMapper;

    @Override
    public List<BusiSysUserRolePojo> select(BusiSysUserRolePojo busiSysUserRolePojo) {
        busiSysUserRolePojo.setDelFlag((short)0);
        return sysUserRoleInfoMapper.select(busiSysUserRolePojo);
    }
}