package com.yinhetianze.pojo.back;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_sys_banner")
public class SysBannerPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "link_url")
    private String linkUrl;

    /**
     * 排序号
     */
    private Short sort;

    /**
     *  0 显示  1 不显示
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 图片地址
     */
    @Column(name = "photo_url")
    private String photoUrl;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;


    private String theme;


    private String memo;

    /**
     * 所属业务  0 手机端  1PC端
     */
    @Column(name = "banner_type")
    private Short bannerType;

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
     * @return link_url
     */
    public String getLinkUrl() {
        return linkUrl;
    }

    /**
     * @param linkUrl
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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
     * 获取 0 显示  1 不显示
     *
     * @return is_show -  0 显示  1 不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置 0 显示  1 不显示
     *
     * @param isShow  0 显示  1 不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Short getBannerType() {
        return bannerType;
    }

    public void setBannerType(Short bannerType) {
        this.bannerType = bannerType;
    }

    /**
     * banner图位置 1顶部 2其他
     */
    @Column(name = "banner_position")
    private Short bannerPosition;

    public Short getBannerPosition() {
        return bannerPosition;
    }

    public void setBannerPosition(Short bannerPosition) {
        this.bannerPosition = bannerPosition;
    }
}