package com.yinhetianze.systemservice.mall.model;

import com.yinhetianze.core.business.httprequest.PageModel;


import java.util.Date;
import java.util.List;
import java.util.Map;

public class MobileFloorModel extends PageModel {

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
     * 手机楼层链接
     */
    private String mobileFloorLink;

    /**
     * 楼层名称
     */
    private String mobileFloorName;

    /**
     * 0=显示 1=不显示
     */
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */
    private Short delFlag;

    /**
     * 图片地址
     */
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

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
     * 获取手机楼层链接
     *
     * @return mobile_floor_link - 手机楼层链接
     */
    public String getMobileFloorLink() {
        return mobileFloorLink;
    }

    /**
     * 设置手机楼层链接
     *
     * @param mobileFloorLink 手机楼层链接
     */
    public void setMobileFloorLink(String mobileFloorLink) {
        this.mobileFloorLink = mobileFloorLink;
    }

    /**
     * 获取楼层名称
     *
     * @return mobile_floor_name - 楼层名称
     */
    public String getMobileFloorName() {
        return mobileFloorName;
    }

    /**
     * 设置楼层名称
     *
     * @param mobileFloorName 楼层名称
     */
    public void setMobileFloorName(String mobileFloorName) {
        this.mobileFloorName = mobileFloorName;
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

    private List<Map> mobileFloor;

    public List<Map> getMobileFloor() {
        return mobileFloor;
    }

    public void setMobileFloor(List<Map> mobileFloor) {
        this.mobileFloor = mobileFloor;
    }
}
