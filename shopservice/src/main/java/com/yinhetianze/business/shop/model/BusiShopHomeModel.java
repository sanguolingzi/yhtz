package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

public class BusiShopHomeModel extends BasicModel{
    private Integer id;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺二维码
     */
    private String shopQrcode;

    /**
     * 店铺LOGO
     */
    private String shopLogo;


    /**
     * 0 审核通过 1 审核不通过  2待审核
     */
    private Short auditStatus;

    /**
     * 线下店铺支付二维码
     */
    private String linePayQrcode;

    /**
     * 所属消费者/会员
     */
    private Integer customerId;

    /**
     * 所属企业id
     */
    private Integer companyinfoId;

    /**
     * 店铺店铺 是否显示 0 显示 1不显示
     */
    private Short phoneShow;

    private String shopPhone;

    /**
     * 店铺访问次数
     */
    private Integer visitorCount;

    /**
     * 店铺收藏数
     */
    private Integer shopCollectCount;

    /**
     * 最新订单
     */
    private Integer newOrders;

    /**
     * 售后订单
     */
    private Integer cancelOrders;
    /**
     * 获取店铺名称
     *
     * @return shop_name - 店铺名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置店铺名称
     *
     * @param shopName 店铺名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopQrcode() {
        return shopQrcode;
    }

    public void setShopQrcode(String shopQrcode) {
        this.shopQrcode = shopQrcode;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public Short getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getLinePayQrcode() {
        return linePayQrcode;
    }

    public void setLinePayQrcode(String linePayQrcode) {
        this.linePayQrcode = linePayQrcode;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCompanyinfoId() {
        return companyinfoId;
    }

    public void setCompanyinfoId(Integer companyinfoId) {
        this.companyinfoId = companyinfoId;
    }

    public Short getPhoneShow() {
        return phoneShow;
    }

    public void setPhoneShow(Short phoneShow) {
        this.phoneShow = phoneShow;
    }

    public Integer getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(Integer visitorCount) {
        this.visitorCount = visitorCount;
    }

    public Integer getShopCollectCount() {
        return shopCollectCount;
    }

    public void setShopCollectCount(Integer shopCollectCount) {
        this.shopCollectCount = shopCollectCount;
    }

    public Integer getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(Integer newOrders) {
        this.newOrders = newOrders;
    }

    public Integer getCancelOrders() {
        return cancelOrders;
    }

    public void setCancelOrders(Integer cancelOrders) {
        this.cancelOrders = cancelOrders;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }
}