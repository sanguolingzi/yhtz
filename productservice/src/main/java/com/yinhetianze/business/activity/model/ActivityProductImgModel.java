package com.yinhetianze.business.activity.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;

public class ActivityProductImgModel  extends PageModel{
    private Integer id;

    /**
     * 关联的活动商品的id
     */
    private Integer actProdId;

    /**
     * 关联的活动id
     */
    private Integer actId;

    /**
     * 商品图片连接地址
     */
    private String prodImg;

    /**
     * 排序标志
     */
    private Short sort;

    /**
     * 是否删除，0未删除，1已删除，默认0未删除
     */
    private Short delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者ID
     */
    private Integer createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者ID
     */
    private Integer updateUser;

    /**
     * 商品轮播图数组
     */
    private String[] productName;

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
     * 获取关联的活动商品的id
     *
     * @return act_prod_id - 关联的活动商品的id
     */
    public Integer getActProdId() {
        return actProdId;
    }

    /**
     * 设置关联的活动商品的id
     *
     * @param actProdId 关联的活动商品的id
     */
    public void setActProdId(Integer actProdId) {
        this.actProdId = actProdId;
    }

    /**
     * 获取关联的活动id
     *
     * @return act_id - 关联的活动id
     */
    public Integer getActId() {
        return actId;
    }

    /**
     * 设置关联的活动id
     *
     * @param actId 关联的活动id
     */
    public void setActId(Integer actId) {
        this.actId = actId;
    }

    /**
     * 获取商品图片连接地址
     *
     * @return prod_img - 商品图片连接地址
     */
    public String getProdImg() {
        return prodImg;
    }

    /**
     * 设置商品图片连接地址
     *
     * @param prodImg 商品图片连接地址
     */
    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }

    /**
     * 获取排序标志
     *
     * @return sort - 排序标志
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序标志
     *
     * @param sort 排序标志
     */
    public void setSort(Short sort) {
        this.sort = sort;
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
     * 获取创建者ID
     *
     * @return create_user - 创建者ID
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建者ID
     *
     * @param createUser 创建者ID
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取更新者ID
     *
     * @return update_user - 更新者ID
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新者ID
     *
     * @param updateUser 更新者ID
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String[] getProductName() {
        return productName;
    }

    public void setProductName(String[] productName) {
        this.productName = productName;
    }
}
