package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

public class ProductFresherImgModel extends BasicModel {
    private Integer id;

    /**
     * 关联商品ID
     */
    private Integer prodId;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 商品图片
     */
    private String prodImg;

    /**
     * 是否删除，0未删除，1已删除，默认0未删除
     */
    private Short delFlag;

    /**
     * 商品轮播图数组
     */
    private String[] productImgs;

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
     * 获取关联商品ID
     *
     * @return prod_id - 关联商品ID
     */
    public Integer getProdId() {
        return prodId;
    }

    /**
     * 设置关联商品ID
     *
     * @param prodId 关联商品ID
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    /**
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取商品图片
     *
     * @return prod_img - 商品图片
     */
    public String getProdImg() {
        return prodImg;
    }

    /**
     * 设置商品图片
     *
     * @param prodImg 商品图片
     */
    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }

    /**
     * 获取是否删除，0未删除，1已删除，默认0未删除
     *
     * @return del_flag - 是否删除，0未删除，1已删除，默认0未删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除，0未删除，1已删除，默认0未删除
     *
     * @param delFlag 是否删除，0未删除，1已删除，默认0未删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    public String[] getProductImgs() {
        return productImgs;
    }

    public void setProductImgs(String[] productImgs) {
        this.productImgs = productImgs;
    }
}