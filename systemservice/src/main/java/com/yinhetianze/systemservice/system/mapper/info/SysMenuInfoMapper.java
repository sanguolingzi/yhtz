package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.systemservice.system.model.SysMenuModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.SysMenuPojo;

import java.util.List;

public interface SysMenuInfoMapper extends InfoMapper<SysMenuPojo> {

    List<SysMenuModel> selectMenuList(SysMenuModel sysMenuModel);
}