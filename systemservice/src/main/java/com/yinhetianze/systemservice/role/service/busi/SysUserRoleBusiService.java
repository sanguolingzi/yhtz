package com.yinhetianze.systemservice.role.service.busi;

import com.yinhetianze.pojo.back.BusiSysUserRolePojo;

public interface SysUserRoleBusiService
{
    int addInfo(BusiSysUserRolePojo busiSysUserRolePojo);

    int deleteByCondition(Integer userId,Integer roleId);

    int updateInfo(BusiSysUserRolePojo busiSysUserRolePojo);

    int deleteInfo(Integer id);
}