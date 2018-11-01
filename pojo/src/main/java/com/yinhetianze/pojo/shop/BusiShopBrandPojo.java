package com.yinhetianze.pojo.shop;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_shop_brand")
public class BusiShopBrandPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 所属店铺id
     */
    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "del_flag")
    private Short delFlag;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "brand_id")
    private Integer brandId;

    /**
     * 品牌店铺图标
     */
    @Column(name = "shop_brand_img")
    private String shopBrandImg;

    @Column(name = "is_show")
    private Short isShow;

    /**
     * 排序
     */
    private Short sort;
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
     * 获取所属店铺id
     *
     * @return shop_id - 所属店铺id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置所属店铺id
     *
     * @param shopId 所属店铺id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return brand_id
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * @param brandId
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取品牌店铺图标
     *
     * @return shop_brand_img - 品牌店铺图标
     */
    public String getShopBrandImg() {
        return shopBrandImg;
    }

    /**
     * 设置品牌店铺图标
     *
     * @param shopBrandImg 品牌店铺图标
     */
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
}