package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.Date;

public class BusiShopModel extends BasicModel{
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
     * 店铺主图
     */
    private String shopMainPhoto;

    /**
     * 店铺wap主图
     */
    private String shopWapPhoto;
    /**
     * 店铺装饰
     */
    private String shopDecorate;


    /**
     * 店铺简介
     */
    private String shopMemo;

    /**
     * 店铺描述
     */
    private String shopDescription;


    /**
     * 0 审核通过 1 审核不通过  2待审核
     */
    private Short auditStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 线下店铺支付二维码
     */
    private String linePayQrcode;

    /**
     * 邮费
     */
    private BigDecimal postage;

    /**
     * 包邮额度
     */
    private BigDecimal postageFree;

    /**
     * 0 通过  1 未通过 2 待审核
     */
    private Short companyAudit;


    /**
     * 账号
     */
    private String account;

    /**
     * 所属消费者/会员
     */
    private Integer customerId;

    /**
     * 所属企业id
     */
    private Integer companyinfoId;


    /**
     * 0 实体店铺 1 游戏店铺
     */
    private Short shopType;

    /**
     * 店铺电话
     */
    private String shopPhone;

    /**
     * 店铺店铺 是否显示 0 显示 1不显示
     */
    private Short phoneShow;

    /**
     * 店铺访问次数
     */
    private Integer visitorCount;

    /**
     * 店铺收藏数
     */
    private Integer shopCollectCount;

    /**
     * 主营商品
     */
    private String shopMainProduct;

    /**
     * 店铺关联企业所在地
     */
    private String place;

    /**
     * 企业营业执照
     */
    private String businessLicense;
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


    /**
     * 获取店铺二维码
     *
     * @return shop_qrcode - 店铺二维码
     */
    public String getShopQrcode() {
        return shopQrcode;
    }

    /**
     * 设置店铺二维码
     *
     * @param shopQrcode 店铺二维码
     */
    public void setShopQrcode(String shopQrcode) {
        this.shopQrcode = shopQrcode;
    }

    /**
     * 获取店铺LOGO
     *
     * @return shop_logo - 店铺LOGO
     */
    public String getShopLogo() {
        return shopLogo;
    }

    /**
     * 设置店铺LOGO
     *
     * @param shopLogo 店铺LOGO
     */
    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    /**
     * 获取店铺简介
     *
     * @return shop_memo - 店铺简介
     */
    public String getShopMemo() {
        return shopMemo;
    }

    /**
     * 设置店铺简介
     *
     * @param shopMemo 店铺简介
     */
    public void setShopMemo(String shopMemo) {
        this.shopMemo = shopMemo;
    }

    /**
     * 获取店铺描述
     *
     * @return shop_description - 店铺描述
     */
    public String getShopDescription() {
        return shopDescription;
    }

    /**
     * 设置店铺描述
     *
     * @param shopDescription 店铺描述
     */
    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
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
     * 获取线下店铺支付二维码
     *
     * @return line_pay_qrcode - 线下店铺支付二维码
     */
    public String getLinePayQrcode() {
        return linePayQrcode;
    }

    /**
     * 设置线下店铺支付二维码
     *
     * @param linePayQrcode 线下店铺支付二维码
     */
    public void setLinePayQrcode(String linePayQrcode) {
        this.linePayQrcode = linePayQrcode;
    }

    /**
     * 获取邮费
     *
     * @return postage - 邮费
     */
    public BigDecimal getPostage() {
        return postage;
    }

    /**
     * 设置邮费
     *
     * @param postage 邮费
     */
    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    /**
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }


    /**
     * 获取0 线上店铺 1 线下店铺
     *
     * @return shop_type - 0 线上店铺 1 线下店铺
     */
    public Short getShopType() {
        return shopType;
    }

    /**
     * 设置0 线上店铺 1 线下店铺
     *
     * @param shopType 0 线上店铺 1 线下店铺
     */
    public void setShopType(Short shopType) {
        this.shopType = shopType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopMainPhoto() {
        return shopMainPhoto;
    }

    public void setShopMainPhoto(String shopMainPhoto) {
        this.shopMainPhoto = shopMainPhoto;
    }

    public String getShopDecorate() {
        return shopDecorate;
    }

    public void setShopDecorate(String shopDecorate) {
        this.shopDecorate = shopDecorate;
    }

    public BigDecimal getPostageFree() {
        return postageFree;
    }

    public void setPostageFree(BigDecimal postageFree) {
        this.postageFree = postageFree;
    }

    public Short getCompanyAudit() {
        return companyAudit;
    }

    public void setCompanyAudit(Short companyAudit) {
        this.companyAudit = companyAudit;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
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

    public Integer getCompanyinfoId() {
        return companyinfoId;
    }

    public void setCompanyinfoId(Integer companyinfoId) {
        this.companyinfoId = companyinfoId;
    }

    public Integer getShopCollectCount() {
        return shopCollectCount;
    }

    public void setShopCollectCount(Integer shopCollectCount) {
        this.shopCollectCount = shopCollectCount;
    }

    public String getShopMainProduct() {
        return shopMainProduct;
    }

    public void setShopMainProduct(String shopMainProduct) {
        this.shopMainProduct = shopMainProduct;
    }

    public String getShopWapPhoto() {
        return shopWapPhoto;
    }

    public void setShopWapPhoto(String shopWapPhoto) {
        this.shopWapPhoto = shopWapPhoto;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }
}