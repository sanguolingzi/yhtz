package com.yinhetianze.systemservice.menu.service.info;

import com.yinhetianze.pojo.back.BusiSysPerMenuPojo;

import java.util.List;

public interface SysPerMenuInfoService
{
    List<BusiSysPerMenuPojo> selectList(BusiSysPerMenuPojo busiSysPerMenuPojo);
}