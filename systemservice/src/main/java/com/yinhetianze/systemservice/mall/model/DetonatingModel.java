package com.yinhetianze.systemservice.mall.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;

public class DetonatingModel extends PageModel {
    private Integer id;

    /**
     * 展示名称
     */
    private String detonatingName;

    /**
     * 图片地址
     */
    private String photoUrl;

    /**
     * 关联的商品ID
     */
    private Integer productId;

    /**
     * 是否首页显示，0显示，1不显示，默认1不显示
     */
    private Short isShow;

    /**
     * 排序编号
     */
    private Short sort;

    /**
     * 是否删除，1已删除，0未删除，默认0未删除
     */
    private Short delFlag;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新操作人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

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
     * 获取展示名称
     *
     * @return detonating_name - 展示名称
     */
    public String getDetonatingName() {
        return detonatingName;
    }

    /**
     * 设置展示名称
     *
     * @param detonatingName 展示名称
     */
    public void setDetonatingName(String detonatingName) {
        this.detonatingName = detonatingName;
    }

    /**
     * 获取图片地址
     *
     * @return photo_url - 图片地址
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 设置图片地址
     *
     * @param photoUrl 图片地址
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 获取关联的商品ID
     *
     * @return product_id - 关联的商品ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置关联的商品ID
     *
     * @param productId 关联的商品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取是否首页显示，0显示，1不显示，默认1不显示
     *
     * @return is_show - 是否首页显示，0显示，1不显示，默认1不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置是否首页显示，0显示，1不显示，默认1不显示
     *
     * @param isShow 是否首页显示，0显示，1不显示，默认1不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取排序编号
     *
     * @return sort - 排序编号
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序编号
     *
     * @param sort 排序编号
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取是否删除，1已删除，0未删除，默认0未删除
     *
     * @return del_flag - 是否删除，1已删除，0未删除，默认0未删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除，1已删除，0未删除，默认0未删除
     *
     * @param delFlag 是否删除，1已删除，0未删除，默认0未删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取创建人
     *
     * @return create_user - 创建人
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建人
     *
     * @param createUser 创建人
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
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
     * 获取更新操作人
     *
     * @return update_user - 更新操作人
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新操作人
     *
     * @param updateUser 更新操作人
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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
}
