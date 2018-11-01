package com.yinhetianze.systemservice.user.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

public class BusiSysUpdatepasswordModel extends BasicModel{
    private Integer id;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 确认登录密码
     */
    private String confirmPassword;

    /**
     * 新密码
     */
    private String newPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}