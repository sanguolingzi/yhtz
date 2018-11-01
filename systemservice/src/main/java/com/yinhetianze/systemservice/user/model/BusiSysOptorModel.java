package com.yinhetianze.systemservice.user.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.Date;

public class BusiSysOptorModel extends BasicModel{
    private Integer id;

    private String account;

    /**
     * 是否重置密码  1 否 0 是
     */
    private Short resetPassword;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 账号状态 0 未冻结 1已冻结 
     */
    private Short accountStatus;

    /**
     * 姓名
     */
    private String optorName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 角色名称 ,分隔
     */
    private String roleNames;

    /**
     * 角色id ,分隔
     */
    private String roleIds;


    private Date createTime;

    private Integer createUser;

    private Integer updateUser;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    public Short getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(Short resetPassword) {
        this.resetPassword = resetPassword;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    /**
     * 获取账号状态 0 未冻结 1已冻结 
     *
     * @return account_status - 账号状态 0 未冻结 1已冻结 
     */
    public Short getAccountStatus() {
        return accountStatus;
    }

    /**
     * 设置账号状态 0 未冻结 1已冻结 
     *
     * @param accountStatus 账号状态 0 未冻结 1已冻结 
     */
    public void setAccountStatus(Short accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * 获取姓名
     *
     * @return optor_name - 姓名
     */
    public String getOptorName() {
        return optorName;
    }

    /**
     * 设置姓名
     *
     * @param optorName 姓名
     */
    public void setOptorName(String optorName) {
        this.optorName = optorName;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {

        return roleNames;
    }

    public void setRoleName(String roleNames) {
        this.roleNames = roleNames;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}