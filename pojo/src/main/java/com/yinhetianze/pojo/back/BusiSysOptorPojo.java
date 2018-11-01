package com.yinhetianze.pojo.back;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_sys_optor")
public class BusiSysOptorPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String account;

    /**
     * 登录密码   
     */
    @Column(name = "login_password")
    private String loginPassword;

    /**
     * 账号状态 0 未冻结 1已冻结 
     */
    @Column(name = "account_status")
    private Short accountStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 姓名
     */
    @Column(name = "optor_name")
    private String optorName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String comments;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

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

    /**
     * 获取登录密码   
     *
     * @return login_password - 登录密码   
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * 设置登录密码   
     *
     * @param loginPassword 登录密码   
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
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
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取电话
     *
     * @return comments - 电话
     */
    public String getComments() {
        return comments;
    }

    /**
     * 设置电话
     *
     * @param comments 电话
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return create_user
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * @return update_user
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取0 正常 1 已删除
     *
     * @return del_flag - 0 正常 1 已删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置0 正常 1 已删除
     *
     * @param delFlag 0 正常 1 已删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }
}