package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.PageModel;

public class BusiShopCompanyPageModel extends PageModel {
    private Integer id;

    /**
     * 公司名称  
     */
    private String companyName;

    private String customerId;

    private String phone;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 注册 开始时间
     */
    private String regeisterBeginTime;

    /**
     * 注册 结束时间
     */
    private String regeisterEndTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRegeisterBeginTime() {
        return regeisterBeginTime;
    }

    public void setRegeisterBeginTime(String regeisterBeginTime) {
        this.regeisterBeginTime = regeisterBeginTime;
    }

    public String getRegeisterEndTime() {
        return regeisterEndTime;
    }

    public void setRegeisterEndTime(String regeisterEndTime) {
        this.regeisterEndTime = regeisterEndTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}