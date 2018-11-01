package com.yinhetianze.systemservice.permission.service.busi;

import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.core.business.BusinessException;

public interface SysPermissionBusiService
{
    int addInfo(BusiSysPermissionModel busiSysPermissionModel);

    int updateInfo(BusiSysPermissionModel busiSysPermissionModel) throws BusinessException;

    int deleteInfo(Integer id);
}