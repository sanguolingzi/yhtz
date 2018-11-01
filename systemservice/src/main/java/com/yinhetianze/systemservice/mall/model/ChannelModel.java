package com.yinhetianze.systemservice.mall.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;


public class ChannelModel extends PageModel{

    private Integer id;

    /**
     * 频道名称
     */

    private String channelName;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 频道链接
     */

    private String channelLink;

    /**
     * 频道图片
     */

    private String channelImage;

    /**
     * 0.显示 1.不显示
     */

    private Short isShow;


    private Integer createUser;

    private String createUserName;

    private Date createTime;


    private Integer updateUser;


    private Date updateTime;

    /**
     * 链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     */
    private Short  linkMarkup;

    /**
     * 链接参数
     */
    private String linkParameter;

    public String getLinkParameter() {
        return linkParameter;
    }

    public void setLinkParameter(String linkParameter) {
        this.linkParameter = linkParameter;
    }

    public Short getLinkMarkup() {
        return linkMarkup;
    }

    public void setLinkMarkup(Short linkMarkup) {
        this.linkMarkup = linkMarkup;
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
     * 获取频道名称
     *
     * @return channel_name - 频道名称
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 设置频道名称
     *
     * @param channelName 频道名称
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
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
     * 获取频道链接
     *
     * @return channel_link - 频道链接
     */
    public String getChannelLink() {
        return channelLink;
    }

    /**
     * 设置频道链接
     *
     * @param channelLink 频道链接
     */
    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }

    /**
     * 获取频道图片
     *
     * @return channel_image - 频道图片
     */
    public String getChannelImage() {
        return channelImage;
    }

    /**
     * 设置频道图片
     *
     * @param channelImage 频道图片
     */
    public void setChannelImage(String channelImage) {
        this.channelImage = channelImage;
    }

    /**
     * 获取0.显示 1.不显示
     *
     * @return is_show - 0.显示 1.不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置0.显示 1.不显示
     *
     * @param isShow 0.显示 1.不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
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
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}