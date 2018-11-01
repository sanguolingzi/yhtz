package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.PageModel;

public class BusiShopPageModel extends PageModel {
    private Integer id;

    /**
     * 店铺名称
     */
    private String shopName;

    private Short auditStatus;

    private Integer customerId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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
}