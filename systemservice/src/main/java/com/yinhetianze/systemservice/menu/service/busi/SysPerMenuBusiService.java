package com.yinhetianze.systemservice.menu.service.busi;

import com.yinhetianze.systemservice.menu.model.BusiSysPerMenuModel;
import com.yinhetianze.core.business.BusinessException;

public interface SysPerMenuBusiService
{
    int addInfo(BusiSysPerMenuModel busiSysPerMenuModel);

    int updateInfo(BusiSysPerMenuModel busiSysPerMenuModel)  throws BusinessException;

    int deleteInfo(Integer id);
}