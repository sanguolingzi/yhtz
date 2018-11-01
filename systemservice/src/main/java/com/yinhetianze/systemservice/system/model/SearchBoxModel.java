package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;


public class SearchBoxModel extends PageModel{

    private Integer id;

    /**
     * 搜索名称
     */

    private String searchName;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 0 显示 1 不显示
     */
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */

    private Short delFlag;


    private Integer createUser;

    private String createUserName;

    private Date createTime;

    private Integer updateUser;

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
     * 获取搜索名称
     *
     * @return search_name - 搜索名称
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * 设置搜索名称
     *
     * @param searchName 搜索名称
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
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
     * 获取0 显示 1 不显示
     *
     * @return is_show - 0 显示 1 不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置0 显示 1 不显示
     *
     * @param isShow 0 显示 1 不显示
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