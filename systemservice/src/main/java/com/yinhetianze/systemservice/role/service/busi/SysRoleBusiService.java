package com.yinhetianze.systemservice.role.service.busi;

import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.core.business.BusinessException;

public interface SysRoleBusiService
{
    int addInfo(BusiSysRoleModel busiSysRoleModel);

    int updateInfo(BusiSysRoleModel busiSysRoleModel) throws BusinessException;

    int deleteInfo(Integer id);
}