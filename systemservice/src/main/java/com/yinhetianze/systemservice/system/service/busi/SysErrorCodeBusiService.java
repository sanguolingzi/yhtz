package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.pojo.back.SysErrorCodePojo;

public interface SysErrorCodeBusiService
{
    int updateByPrimaryKeySelective(SysErrorCodePojo sysErrorCodePojo);

    int insertSelective(SysErrorCodePojo sysErrorCodePojo);

    int deleteSelective(SysErrorCodePojo sysErrorCodePojo);
}