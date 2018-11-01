package com.yinhetianze.systemservice.menu.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

public class BusiSysPerMenuModel extends BasicModel {

    private String menuIds;

    private Integer permissionId;

    private String permName;

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }
}