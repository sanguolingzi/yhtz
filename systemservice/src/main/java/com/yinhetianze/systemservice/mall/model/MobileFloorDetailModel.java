package com.yinhetianze.systemservice.mall.model;

import com.yinhetianze.core.business.httprequest.PageModel;
import java.util.Date;

public class MobileFloorDetailModel extends PageModel {

    private Integer id;


    private Integer createUser;


    private Integer updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     */
    private Short linkMarkup;

    /**
     * 链接参数
     */
    private String linkParameter;

    /**
     * 手机楼层内容链接
     */
    private String mobileFloorDetailLink;

    /**
     * 楼层ID
     */
    private Integer mobileFloorId;

    /**
     * 图片地址
     */
    private String photoUrl;

    /**
     * 0=显示 1=不显示
     */
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */
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
     * @return create_user
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * @return update_user
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * 获取链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     *
     * @return link_markup - 链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     */
    public Short getLinkMarkup() {
        return linkMarkup;
    }

    /**
     * 设置链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     *
     * @param linkMarkup 链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     */
    public void setLinkMarkup(Short linkMarkup) {
        this.linkMarkup = linkMarkup;
    }

    /**
     * 获取链接参数
     *
     * @return link_parameter - 链接参数
     */
    public String getLinkParameter() {
        return linkParameter;
    }

    /**
     * 设置链接参数
     *
     * @param linkParameter 链接参数
     */
    public void setLinkParameter(String linkParameter) {
        this.linkParameter = linkParameter;
    }

    /**
     * 获取手机楼层内容链接
     *
     * @return mobile_floor_detail_link - 手机楼层内容链接
     */
    public String getMobileFloorDetailLink() {
        return mobileFloorDetailLink;
    }

    /**
     * 设置手机楼层内容链接
     *
     * @param mobileFloorDetailLink 手机楼层内容链接
     */
    public void setMobileFloorDetailLink(String mobileFloorDetailLink) {
        this.mobileFloorDetailLink = mobileFloorDetailLink;
    }

    /**
     * 获取楼层ID
     *
     * @return mobile_floor_id - 楼层ID
     */
    public Integer getMobileFloorId() {
        return mobileFloorId;
    }

    /**
     * 设置楼层ID
     *
     * @param mobileFloorId 楼层ID
     */
    public void setMobileFloorId(Integer mobileFloorId) {
        this.mobileFloorId = mobileFloorId;
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
     * 获取0=显示 1=不显示
     *
     * @return is_show - 0=显示 1=不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置0=显示 1=不显示
     *
     * @param isShow 0=显示 1=不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
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
