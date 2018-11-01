package com.yinhetianze.systemservice.role.service.info;

import com.yinhetianze.pojo.back.BusiSysUserRolePojo;

import java.util.List;

public interface SysUserRoleInfoService
{
    List<BusiSysUserRolePojo> select(BusiSysUserRolePojo busiSysUserRolePojo);
}