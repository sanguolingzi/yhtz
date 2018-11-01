package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.SysMenuPojo;

public interface SysMenuBusiService
{
    int insertSelective(SysMenuPojo sysMenuPojo);

    int updateByPrimaryKeySelective(SysMenuPojo sysMenuPojo);

    int deleteBatch(String s) throws BusinessException;
}