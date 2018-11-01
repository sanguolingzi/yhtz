package com.yinhetianze.systemservice.role.service.info;

import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.systemservice.role.model.BusiSysRolePageModel;
import com.yinhetianze.pojo.back.BusiSysRolePojo;

import java.util.List;

public interface SysRoleInfoService
{
    BusiSysRolePojo selectOne(BusiSysRolePojo busiSysRolePojo);

    List<BusiSysRoleModel> selectSysRoleList(BusiSysRolePageModel busiSysRolePageModel);
}