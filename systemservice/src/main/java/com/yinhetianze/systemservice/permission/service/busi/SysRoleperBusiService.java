package com.yinhetianze.systemservice.permission.service.busi;

import com.yinhetianze.systemservice.permission.model.BusiSysRoleperModel;

public interface SysRoleperBusiService
{

    int updateInfo(BusiSysRoleperModel busiSysRoleperModel);


    int deleteInfo(Integer id);
}