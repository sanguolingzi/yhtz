package com.yinhetianze.systemservice.role.service.busi.impl;

import com.yinhetianze.systemservice.role.mapper.busi.SysUserRoleBusiMapper;
import com.yinhetianze.pojo.back.BusiSysUserRolePojo;
import com.yinhetianze.systemservice.role.service.busi.SysUserRoleBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleBusiServiceImpl implements SysUserRoleBusiService
{
    @Autowired
    private SysUserRoleBusiMapper sysUserRoleBusiMapper;

    @Override
    public int addInfo(BusiSysUserRolePojo busiSysUserRolePojo) {

        return sysUserRoleBusiMapper.insertSelective(busiSysUserRolePojo);
    }

    @Override
    public int deleteByCondition(Integer userId, Integer roleId) {
        return sysUserRoleBusiMapper.deleteByCondition(userId,roleId);
    }

    @Override
    public int updateInfo(BusiSysUserRolePojo busiSysUserRolePojo) {
        return sysUserRoleBusiMapper.updateByPrimaryKeySelective(busiSysUserRolePojo);
    }

    @Override
    public int deleteInfo(Integer id) {
        BusiSysUserRolePojo busiSysUserRolePojo = new BusiSysUserRolePojo();
        busiSysUserRolePojo.setId(id);
        busiSysUserRolePojo.setDelFlag((short)1);
        return sysUserRoleBusiMapper.updateByPrimaryKeySelective(busiSysUserRolePojo);
    }
}