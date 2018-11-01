package com.yinhetianze.systemservice.role.service.info.impl;

import com.yinhetianze.systemservice.role.mapper.info.SysRoleInfoMapper;
import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.systemservice.role.model.BusiSysRolePageModel;
import com.yinhetianze.pojo.back.BusiSysRolePojo;
import com.yinhetianze.systemservice.role.service.info.SysRoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleInfoServiceImpl implements SysRoleInfoService
{
    @Autowired
    private SysRoleInfoMapper sysRoleInfoMapper;

    @Override
    public BusiSysRolePojo selectOne(BusiSysRolePojo busiSysRolePojo) {
        busiSysRolePojo.setDelFlag((short)0);
        return sysRoleInfoMapper.selectOne(busiSysRolePojo);
    }

    @Override
    public List<BusiSysRoleModel> selectSysRoleList(BusiSysRolePageModel busiSysRolePageModel) {
        return sysRoleInfoMapper.selectSysRoleList(busiSysRolePageModel);
    }
}