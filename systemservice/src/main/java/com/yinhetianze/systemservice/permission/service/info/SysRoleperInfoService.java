package com.yinhetianze.systemservice.permission.service.info;

import com.yinhetianze.pojo.back.BusiSysRoleperPojo;
import java.util.List;
public interface SysRoleperInfoService
{
    List<BusiSysRoleperPojo> select(BusiSysRoleperPojo busiSysRoleperPojo);
}