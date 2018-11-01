package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

public class ShopCompanyPageModel extends BasicModel {
    private Integer shopId;

    /**
     * 店铺名称
     */
    private String shopName;
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
     * 所属企业id
     */
    private Integer companyinfoId;
    /**
     * 州省
     */
    private String regionLocation;

    /**
     * 城市
     */
    private String city;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 区域
     */
    private String areaCounty;
    /**
     * 营业执照url
     */
    private String businessLicense;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopMainPhoto() {
        return shopMainPhoto;
    }

    public void setShopMainPhoto(String shopMainPhoto) {
        this.shopMainPhoto = shopMainPhoto;
    }

    public String getShopWapPhoto() {
        return shopWapPhoto;
    }

    public void setShopWapPhoto(String shopWapPhoto) {
        this.shopWapPhoto = shopWapPhoto;
    }

    public String getShopDecorate() {
        return shopDecorate;
    }

    public void setShopDecorate(String shopDecorate) {
        this.shopDecorate = shopDecorate;
    }

    public String getShopMemo() {
        return shopMemo;
    }

    public void setShopMemo(String shopMemo) {
        this.shopMemo = shopMemo;
    }

    public Integer getCompanyinfoId() {
        return companyinfoId;
    }

    public void setCompanyinfoId(Integer companyinfoId) {
        this.companyinfoId = companyinfoId;
    }

    public String getRegionLocation() {
        return regionLocation;
    }

    public void setRegionLocation(String regionLocation) {
        this.regionLocation = regionLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaCounty() {
        return areaCounty;
    }

    public void setAreaCounty(String areaCounty) {
        this.areaCounty = areaCounty;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }
}
