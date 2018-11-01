package com.yinhetianze.systemservice.permission.service.info.impl;

import com.yinhetianze.systemservice.permission.mapper.info.SysPermissionInfoMapper;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionPageModel;
import com.yinhetianze.pojo.back.BusiSysPermissionPojo;
import com.yinhetianze.systemservice.permission.service.info.SysPermissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionInfoServiceImpl implements SysPermissionInfoService
{
    @Autowired
    private SysPermissionInfoMapper sysPermissionInfoMapper;

    @Override
    public BusiSysPermissionPojo selectOne(BusiSysPermissionPojo busiSysPermissionPojo) {
        busiSysPermissionPojo.setDelFlag((short)0);
        return sysPermissionInfoMapper.selectOne(busiSysPermissionPojo);
    }

    @Override
    public List<BusiSysPermissionModel> selectSysPermissionList(BusiSysPermissionPageModel busiSysPermissionPageModel) {
        return sysPermissionInfoMapper.selectSysPermissionList(busiSysPermissionPageModel);
    }
}