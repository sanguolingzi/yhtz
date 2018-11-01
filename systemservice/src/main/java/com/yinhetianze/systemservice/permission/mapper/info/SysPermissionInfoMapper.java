package com.yinhetianze.systemservice.permission.mapper.info;

import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionPageModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.BusiSysPermissionPojo;

import java.util.List;

public interface SysPermissionInfoMapper extends InfoMapper<BusiSysPermissionPojo> {
    List<BusiSysPermissionModel> selectSysPermissionList(BusiSysPermissionPageModel busiSysRolePermissionModel);
}