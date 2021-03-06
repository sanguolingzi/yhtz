package com.yinhetianze.pojo.product;

import javax.persistence.*;

@Table(name = "busi_product_one_area_img")
public class OneAreaImgPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 所属专区
     */
    @Column(name = "one_area_id")
    private Integer oneAreaId;

    /**
     * 商品图片
     */
    @Column(name = "product_img")
    private String productImg;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

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
     * 获取所属专区
     *
     * @return one_area_id - 所属专区
     */
    public Integer getOneAreaId() {
        return oneAreaId;
    }

    /**
     * 设置所属专区
     *
     * @param oneAreaId 所属专区
     */
    public void setOneAreaId(Integer oneAreaId) {
        this.oneAreaId = oneAreaId;
    }

    /**
     * 获取商品图片
     *
     * @return product_img - 商品图片
     */
    public String getProductImg() {
        return productImg;
    }

    /**
     * 设置商品图片
     *
     * @param productImg 商品图片
     */
    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * 获取0 正常 1 已删除
     *
     * @return del_flag - 0 正常 1 已删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置0 正常 1 已删除
     *
     * @param delFlag 0 正常 1 已删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }
}