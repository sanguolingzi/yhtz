package com.yinhetianze.pojo.back;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_sys_floor")
public class SysFloorPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 楼层名称
     */
    @Column(name = "floor_name")
    private String floorName;

    /**
     * 排序号
     */
    private Short sort;

    @Column(name = "create_user")
    private Integer createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     *  0 显示  1 不显示
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;
    /**
     * 0 手机端 1 PC端
     */
    @Column(name = "floor_type")
    private Short floorType;

    /**
     * 楼层大图
     */
    @Column(name = "photo_url")
    private String photoUrl;

    /**
     * 楼层大图
     */
    @Column(name = "link_markup")
    private Short linkMarkup;
    /**
     * 楼层大图
     */
    @Column(name = "link_parameter")
    private String linkParameter;
    /**
     * 楼层大图
     */
    @Column(name = "floor_link")
    private String floorLink;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Short getLinkMarkup() {
        return linkMarkup;
    }

    public void setLinkMarkup(Short linkMarkup) {
        this.linkMarkup = linkMarkup;
    }

    public String getLinkParameter() {
        return linkParameter;
    }

    public void setLinkParameter(String linkParameter) {
        this.linkParameter = linkParameter;
    }

    public String getFloorLink() {
        return floorLink;
    }

    public void setFloorLink(String floorLink) {
        this.floorLink = floorLink;
    }

    public Short getFloorType() {
        return floorType;
    }

    public void setFloorType(Short floorType) {
        this.floorType = floorType;
    }

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
     * 获取楼层名称
     *
     * @return floor_name - 楼层名称
     */
    public String getFloorName() {
        return floorName;
    }

    /**
     * 设置楼层名称
     *
     * @param floorName 楼层名称
     */
    public void setFloorName(String floorName) {
        this.floorName = floorName;
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