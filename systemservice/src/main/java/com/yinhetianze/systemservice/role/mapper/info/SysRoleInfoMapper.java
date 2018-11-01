package com.yinhetianze.systemservice.role.mapper.info;

import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.systemservice.role.model.BusiSysRolePageModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.BusiSysRolePojo;

import java.util.List;

public interface SysRoleInfoMapper extends InfoMapper<BusiSysRolePojo> {

    List<BusiSysRoleModel> selectSysRoleList(BusiSysRolePageModel busiSysRolePageModel);
}