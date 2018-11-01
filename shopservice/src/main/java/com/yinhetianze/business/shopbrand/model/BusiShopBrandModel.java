package com.yinhetianze.business.shopbrand.model;

import com.yinhetianze.core.business.httprequest.PageModel;

/**
 * 商品品牌model
 */
public class BusiShopBrandModel extends PageModel
{
    private Integer id;
    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 店铺品牌图片
     */
    private String shopBrandImg;

    private Short isShow;

    private Short sort;

    /**
     * 是否自己添加
     */
    private Short addSelf;

    private Integer shopId;

    public Integer getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Integer brandId)
    {
        this.brandId = brandId;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public String getShopBrandImg() {
        return shopBrandImg;
    }

    public void setShopBrandImg(String shopBrandImg) {
        this.shopBrandImg = shopBrandImg;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Short getIsShow() {
        return isShow;
    }

    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Short getAddSelf() {
        return addSelf;
    }

    public void setAddSelf(Short addSelf) {
        this.addSelf = addSelf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
