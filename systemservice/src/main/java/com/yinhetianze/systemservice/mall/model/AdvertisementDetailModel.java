package com.yinhetianze.systemservice.mall.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;

public class AdvertisementDetailModel extends PageModel {

    private Integer id;

    /**
     * 广告ID
     */
    private Integer advertisementId;

    /**
     * 广告详情图片
     */
    private String advertisementDetailImg;

    /**
     * 广告详情链接
     */
    private String advertisementDetailLink;

    /**
     * 链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     */
    private Short linkMarkup;

    /**
     * 链接参数
     */
    private String linkParameter;

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
     * 获取广告ID
     *
     * @return advertisement_id - 广告ID
     */
    public Integer getAdvertisementId() {
        return advertisementId;
    }

    /**
     * 设置广告ID
     *
     * @param advertisementId 广告ID
     */
    public void setAdvertisementId(Integer advertisementId) {
        this.advertisementId = advertisementId;
    }

    /**
     * 获取广告详情图片
     *
     * @return advertisement_detail_img - 广告详情图片
     */
    public String getAdvertisementDetailImg() {
        return advertisementDetailImg;
    }

    /**
     * 设置广告详情图片
     *
     * @param advertisementDetailImg 广告详情图片
     */
    public void setAdvertisementDetailImg(String advertisementDetailImg) {
        this.advertisementDetailImg = advertisementDetailImg;
    }

    /**
     * 获取广告详情链接
     *
     * @return advertisement_detail_link - 广告详情链接
     */
    public String getAdvertisementDetailLink() {
        return advertisementDetailLink;
    }

    /**
     * 设置广告详情链接
     *
     * @param advertisementDetailLink 广告详情链接
     */
    public void setAdvertisementDetailLink(String advertisementDetailLink) {
        this.advertisementDetailLink = advertisementDetailLink;
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
