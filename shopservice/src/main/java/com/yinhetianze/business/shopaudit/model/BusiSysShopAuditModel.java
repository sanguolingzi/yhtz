package com.yinhetianze.business.shopaudit.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.Date;

public class BusiSysShopAuditModel extends BasicModel{
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

    private Integer updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 0 审核通过 1 审核不通过  2待审核
     */
    private Short auditStatus;

    private Integer customerId;
    /**
     * 审核前状态
     */
    private Short onceAuditStatus;

    /**
     * 审核内容 json串
     */
    private String auditContent;

    /**
     * '0 企业审核 1店铺审核 2综合审核
     */
    private Short auditType;

    /**
     * 审核内容 单项结果 json存储
     */
    private String auditResultJson;

    /**
     * 关联id
     */
    private Integer relationId;


    private Short doUpdate;

    private Short auditStep;
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
     * 获取审核前状态
     *
     * @return once_audit_status - 审核前状态
     */
    public Short getOnceAuditStatus() {
        return onceAuditStatus;
    }

    /**
     * 设置审核前状态
     *
     * @param onceAuditStatus 审核前状态
     */
    public void setOnceAuditStatus(Short onceAuditStatus) {
        this.onceAuditStatus = onceAuditStatus;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public Short getAuditType() {
        return auditType;
    }

    public void setAuditType(Short auditType) {
        this.auditType = auditType;
    }

    public String getAuditResultJson() {
        return auditResultJson;
    }

    public void setAuditResultJson(String auditResultJson) {
        this.auditResultJson = auditResultJson;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Short getDoUpdate() {
        return doUpdate;
    }

    public void setDoUpdate(Short doUpdate) {
        this.doUpdate = doUpdate;
    }

    public Short getAuditStep() {
        return auditStep;
    }

    public void setAuditStep(Short auditStep) {
        this.auditStep = auditStep;
    }
}