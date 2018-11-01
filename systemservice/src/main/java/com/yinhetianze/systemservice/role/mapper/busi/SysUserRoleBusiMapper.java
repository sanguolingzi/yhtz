package com.yinhetianze.systemservice.role.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.back.BusiSysUserRolePojo;
import org.apache.ibatis.annotations.Param;

public interface SysUserRoleBusiMapper extends BusiMapper<BusiSysUserRolePojo> {

    int deleteByCondition(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
}