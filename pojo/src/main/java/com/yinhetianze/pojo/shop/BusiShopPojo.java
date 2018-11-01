package com.yinhetianze.pojo.shop;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_shop")
public class BusiShopPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 店铺名称
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 店铺二维码
     */
    @Column(name = "shop_qrcode")
    private String shopQrcode;

    /**
     * 店铺LOGO
     */
    @Column(name = "shop_logo")
    private String shopLogo;

    /**
     * 店铺wap主图
     */
    @Column(name = "shop_wap_photo")
    private String shopWapPhoto;

    /**
     * 店铺主图
     */
    @Column(name = "shop_main_photo")
    private String shopMainPhoto;

    /**
     * 店铺装饰
     */
    @Column(name = "shop_decorate")
    private String shopDecorate;


    /**
     * 店铺简介
     */
    @Column(name = "shop_memo")
    private String shopMemo;

    /**
     * 店铺描述
     */
    @Column(name = "shop_description")
    private String shopDescription;


    /**
     * 0 审核通过 1 审核不通过  2待审核
     */
    @Column(name = "audit_status")
    private Short auditStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 线下店铺支付二维码
     */
    @Column(name = "line_pay_qrcode")
    private String linePayQrcode;

    /**
     * 邮费
     */
    private BigDecimal postage;

    /**
     * 包邮额度
     */
    @Column(name= "postage_free")
    private BigDecimal postageFree;

    /**
     * 0 通过  1 未通过 2 待审核
     */
    @Column(name = "company_audit")
    private Short companyAudit;


    /**
     * 账号
     */
    private String account;

    /**
     * 所属消费者/会员
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 所属企业id
     */
    @Column(name = "companyinfo_id")
    private Integer companyinfoId;

    /**
     * 0 实体店铺 1 游戏店铺
     */
    @Column(name = "shop_type")
    private Short shopType;

    @Column(name = "del_flag")
    private Short delFlag;
    /**
     * 店铺电话
     */
    @Column(name= "shop_phone")
    private String shopPhone;

    /**
     * 店铺店铺 是否显示 0 显示 1不显示
     */
    @Column(name = "phone_show")
    private Short phoneShow;

    /**
     * 店铺访问次数
     */
    @Column(name = "visitor_count")
    private Integer visitorCount;

    /**
     * 主营商品
     */
    @Column(name = "shop_main_product")
    private String shopMainProduct;
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
     * 获取0 通过  1 未通过 2 待审核
     *
     * @return company_audit - 0 通过  1 未通过 2 待审核
     */
    public Short getCompanyAudit() {
        return companyAudit;
    }

    /**
     * 设置0 通过  1 未通过 2 待审核
     *
     * @param companyAudit 0 通过  1 未通过 2 待审核
     */
    public void setCompanyAudit(Short companyAudit) {
        this.companyAudit = companyAudit;
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
     * 获取所属消费者/会员
     *
     * @return customer_id - 所属消费者/会员
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置所属消费者/会员
     *
     * @param customerId 所属消费者/会员
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    /**
     * @return del_flag
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
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
}