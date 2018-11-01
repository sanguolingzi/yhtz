package com.yinhetianze.systemservice.permission.service.info;

import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionPageModel;
import com.yinhetianze.pojo.back.BusiSysPermissionPojo;

import java.util.List;

public interface SysPermissionInfoService
{
    BusiSysPermissionPojo selectOne(BusiSysPermissionPojo busiSysPermissionPojo);

    List<BusiSysPermissionModel> selectSysPermissionList(BusiSysPermissionPageModel busiSysPermissionPageModel);
}