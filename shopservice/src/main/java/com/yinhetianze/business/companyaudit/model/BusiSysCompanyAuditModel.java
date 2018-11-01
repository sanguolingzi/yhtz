package com.yinhetianze.business.companyaudit.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.Date;

public class BusiSysCompanyAuditModel extends BasicModel{
    private Integer id;

    /**
     * 原因
     */
    private String reason;

    /**
     * 创建时间
     */
    private Date createTime;

    private Integer createUser;

    private String createUserName;

    private Integer updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 0 审核通过 1 审核不通过  2待审核
     */
    private Short auditStatus;

    /**
     * 所属企业信息id
     */
    private Integer companyId;

    /**
     * 审核前状态 取 企业信息中修改前状态
     */
    private Short onceAuditStatus;

    /**
     * 内容单项审核结果
     */
    private String auditResultJson;

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
     * 获取原因
     *
     * @return reason - 原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置原因
     *
     * @param reason 原因
     */
    public void setReason(String reason) {
        this.reason = reason;
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
     * 获取0 审核通过 1 审核不通过  2待审核
     *
     * @return audit_status - 0 审核通过 1 审核不通过  2待审核
     */
    public Short getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置0 审核通过 1 审核不通过  2待审核
     *
     * @param auditStatus 0 审核通过 1 审核不通过  2待审核
     */
    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取所属企业信息id
     *
     * @return company_id - 所属企业信息id
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 设置所属企业信息id
     *
     * @param companyId 所属企业信息id
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取审核前状态 取 企业信息中修改前状态
     *
     * @return once_audit_status - 审核前状态 取 企业信息中修改前状态
     */
    public Short getOnceAuditStatus() {
        return onceAuditStatus;
    }

    /**
     * 设置审核前状态 取 企业信息中修改前状态
     *
     * @param onceAuditStatus 审核前状态 取 企业信息中修改前状态
     */
    public void setOnceAuditStatus(Short onceAuditStatus) {
        this.onceAuditStatus = onceAuditStatus;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getAuditResultJson() {
        return auditResultJson;
    }

    public void setAuditResultJson(String auditResultJson) {
        this.auditResultJson = auditResultJson;
    }
}