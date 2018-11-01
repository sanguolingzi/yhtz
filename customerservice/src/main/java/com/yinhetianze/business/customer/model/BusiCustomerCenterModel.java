package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;

/**
 * 个人中心  实体
 */
public class BusiCustomerCenterModel extends BasicModel {

    private Integer customerId;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;



    /**
     * 头像
     */
    private String photoUrl;

    /**
     * 个人二维码图片地址
     */
    private String personQrcode;

    /**
     * 友旗币
     */
    private BigDecimal starCoin;

    /**
     * U 币
     */
    private BigDecimal rewardAmount;

    /**
     * 余额
     */
    private BigDecimal amount;

    /**
     * 收藏数
     */
    private Integer collectNum;

    /**
     * 商品收藏数
     */
    private Integer prodCollectNum;

    /**
     * 店铺收藏数
     */
    private Integer shopCollectNum;

    /**
     * 0 未设置手机号  1已设置手机号
     */
    private Short setPhone;

    /**
     * 是否会员 0 是   1否
     */
    private Short isMember;

    /**
     * 推荐码
     */
    private String recommendCode;


    /**
     * 是否合伙人 0是 1否
     */
    private Short isPartner;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPersonQrcode() {
        return personQrcode;
    }

    public void setPersonQrcode(String personQrcode) {
        this.personQrcode = personQrcode;
    }

    public BigDecimal getStarCoin() {
        return starCoin;
    }

    public void setStarCoin(BigDecimal starCoin) {
        this.starCoin = starCoin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getProdCollectNum() {
        return prodCollectNum;
    }

    public void setProdCollectNum(Integer prodCollectNum) {
        this.prodCollectNum = prodCollectNum;
    }

    public Integer getShopCollectNum() {
        return shopCollectNum;
    }

    public void setShopCollectNum(Integer shopCollectNum) {
        this.shopCollectNum = shopCollectNum;
    }

    public Short getSetPhone() {
        return setPhone;
    }

    public void setSetPhone(Short setPhone) {
        this.setPhone = setPhone;
    }

    public Short getIsMember() {
        return isMember;
    }

    public void setIsMember(Short isMember) {
        this.isMember = isMember;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public Short getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(Short isPartner) {
        this.isPartner = isPartner;
    }
}