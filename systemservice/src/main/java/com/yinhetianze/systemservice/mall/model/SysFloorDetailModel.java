package com.yinhetianze.systemservice.mall.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;

public class SysFloorDetailModel extends PageModel {
    private Integer id;

    /**
     *  0 显示  1 不显示
     */
    private Short isShow;

    private Integer createUser;

    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;

    private Integer updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 图片地址
     */
    private String photoUrl;

    /**
     * 1 正方形 2 长方形
     */
    private Short photoType;

    /**
     * 所属楼层id
     */
    private Integer floorId;

    private Integer contentId;

    /**
     * 0 品牌 1 商品
     */
    private Short contentType;
    /**
     * 描述
     */
    private String description;

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
     * 获取1 正方形 2 长方形
     *
     * @return photo_type - 1 正方形 2 长方形
     */
    public Short getPhotoType() {
        return photoType;
    }

    /**
     * 设置1 正方形 2 长方形
     *
     * @param photoType 1 正方形 2 长方形
     */
    public void setPhotoType(Short photoType) {
        this.photoType = photoType;
    }

    /**
     * 获取所属楼层id
     *
     * @return floor_id - 所属楼层id
     */
    public Integer getFloorId() {
        return floorId;
    }

    /**
     * 设置所属楼层id
     *
     * @param floorId 所属楼层id
     */
    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Short getContentType() {
        return contentType;
    }

    public void setContentType(Short contentType) {
        this.contentType = contentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}