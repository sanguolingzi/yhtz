package com.yinhetianze.business.shopaudit.model;

import com.yinhetianze.core.business.httprequest.PageModel;

public class BusiSysShopAuditPageModel extends PageModel {
    private Integer id;


    /**
     * 0 审核通过 1 审核不通过  2待审核
     */
    private Short auditStatus;

    private Integer customerId;

    /**
     * '0 企业审核 1店铺审核 2综合审核
     */
    private Short auditType;

    /**
     * 关联id
     */
    private Integer relationId;
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


    public Short getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Short getAuditType() {
        return auditType;
    }

    public void setAuditType(Short auditType) {
        this.auditType = auditType;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }
}