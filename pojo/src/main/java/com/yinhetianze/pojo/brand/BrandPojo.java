package com.yinhetianze.pojo.brand;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_brand")
public class BrandPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 品牌名称
     */
    @Column(name = "brand_name")
    private String brandName;

    /**
     * 品牌大图
     */
    @Column(name = "brand_big_img")
    private String brandBigImg;

    /**
     * 品牌小图
     */
    @Column(name = "brand_small_img")
    private String brandSmallImg;

    /**
     * 品牌标题
     */
    private String title;

    /**
     * 品牌简介
     */
    private String synopsis;

    /**
     * 品牌首字母
     */
    @Column(name = "first_word")
    private String firstWord;

    /**
     * 排序编号
     */
    private Integer sort;

    /**
     * 是否有效，1=有效，0=无效
     */
    @Column(name = "is_show")
    private Short isShow;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 品牌描述详情
     */
    @Column(name = "description")
    private String description;


    /**
     * 品牌类型  0 系统品牌 1店铺品牌  店铺品牌可转为系统品牌
     */
    @Column(name = "brand_type")
    private Short brandType;

    /**
     * 品牌店铺图片
     */
    @Column(name = "brand_shop_img")
    private String brandShopImg;
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
     * 获取品牌名称
     *
     * @return brand_name - 品牌名称
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置品牌名称
     *
     * @param brandName 品牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取品牌大图
     *
     * @return brand_big_img - 品牌大图
     */
    public String getBrandBigImg() {
        return brandBigImg;
    }

    /**
     * 设置品牌大图
     *
     * @param brandBigImg 品牌大图
     */
    public void setBrandBigImg(String brandBigImg) {
        this.brandBigImg = brandBigImg;
    }

    /**
     * 获取品牌小图
     *
     * @return brand_small_img - 品牌小图
     */
    public String getBrandSmallImg() {
        return brandSmallImg;
    }

    /**
     * 设置品牌小图
     *
     * @param brandSmallImg 品牌小图
     */
    public void setBrandSmallImg(String brandSmallImg) {
        this.brandSmallImg = brandSmallImg;
    }

    /**
     * 获取品牌标题
     *
     * @return title - 品牌标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置品牌标题
     *
     * @param title 品牌标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取品牌简介
     *
     * @return synopsis - 品牌简介
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * 设置品牌简介
     *
     * @param synopsis 品牌简介
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * 获取品牌首字母
     *
     * @return first_word - 品牌首字母
     */
    public String getFirstWord() {
        return firstWord;
    }

    /**
     * 设置品牌首字母
     *
     * @param firstWord 品牌首字母
     */
    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    /**
     * 获取排序编号
     *
     * @return sort - 排序编号
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序编号
     *
     * @param sort 排序编号
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取是否有效，1=有效，0=无效
     *
     * @return is_show - 是否有效，1=有效，0=无效
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置是否有效，1=有效，0=无效
     *
     * @param isShow 是否有效，1=有效，0=无效
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
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
}