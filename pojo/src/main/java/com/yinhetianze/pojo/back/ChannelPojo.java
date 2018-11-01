package com.yinhetianze.pojo.back;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_channel")
public class ChannelPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 频道名称
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 频道链接
     */
    @Column(name = "channel_link")
    private String channelLink;

    /**
     * 频道图片
     */
    @Column(name = "channel_image")
    private String channelImage;

    /**
     * 0.显示 1.不显示
     */
    @Column(name = "is_show")
    private Short isShow;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private Integer updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 链接标记 0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情 默认0
     */
    @Column(name = "link_markup")
    private Short  linkMarkup;

    /**
     * 链接参数
     */
    @Column(name = "link_parameter")
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