package com.yinhetianze.systemservice.permission.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

public class BusiSysRoleperModel extends BasicModel {

    private Integer roleId;

    private String permissionIds;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }
}