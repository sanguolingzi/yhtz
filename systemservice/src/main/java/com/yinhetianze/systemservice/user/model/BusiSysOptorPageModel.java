package com.yinhetianze.systemservice.user.model;

import com.yinhetianze.core.business.httprequest.PageModel;

public class BusiSysOptorPageModel extends PageModel{
    /**
     * 账号
     */
    private String account;
    /**
     * 姓名
     */
    private String optorName;

    /**
     * 角色
     */
    private Integer roleId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOptorName() {
        return optorName;
    }

    public void setOptorName(String optorName) {
        this.optorName = optorName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}