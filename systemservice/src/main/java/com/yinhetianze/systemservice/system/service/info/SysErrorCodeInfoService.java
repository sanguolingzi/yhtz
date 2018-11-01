package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.pojo.back.SysErrorCodePojo;

import java.util.List;

public interface SysErrorCodeInfoService
{
    List selectList(SysErrorCodePojo sysErrorCodePojo);
}