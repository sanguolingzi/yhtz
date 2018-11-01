package com.yinhetianze.business.brand.model;

import com.yinhetianze.core.business.httprequest.PageModel;

/**
 * 商品分类model
 */
public class BrandModel extends PageModel
{
    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌标题
     */
    private String title;

    /**
     * 品牌简介
     */
    private String synopsis;

    /**
     * 是否显示
     */
    private Short isShow;

    /**
     * 排序编号
     */
    private Integer sort;

    /**
     * 大图地址
     */
    private String bigImg;

    /**
     * 小图地址
     */
    private String smallImg;

    /**
     * 品牌详情描述
     */
    private String description;

    /**
     * 品牌首字母
     */
    private String firstWord;
    /**
     * 0系统品牌 1店铺品牌
     */
    private Short brandType;

    /**
     * 品牌店铺图片
     */
    private String brandShopImg;
    /**
     * 0 正常 1 已删除
     */

    private Short delFlag;

    public Short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    public Short getBrandType() {
        return brandType;
    }

    public void setBrandType(Short brandType) {
        this.brandType = brandType;
    }

    public String getBrandShopImg() {
        return brandShopImg;
    }

    public void setBrandShopImg(String brandShopImg) {
        this.brandShopImg = brandShopImg;
    }


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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSynopsis()
    {
        return synopsis;
    }

    public void setSynopsis(String synopsis)
    {
        this.synopsis = synopsis;
    }

    public Short getIsShow()
    {
        return isShow;
    }

    public void setIsShow(Short isShow)
    {
        this.isShow = isShow;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public String getBigImg()
    {
        return bigImg;
    }

    public void setBigImg(String bigImg)
    {
        this.bigImg = bigImg;
    }

    public String getSmallImg()
    {
        return smallImg;
    }

    public void setSmallImg(String smallImg)
    {
        this.smallImg = smallImg;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getFirstWord()
    {
        return firstWord;
    }

    public void setFirstWord(String firstWord)
    {
        this.firstWord = firstWord;
    }
}
