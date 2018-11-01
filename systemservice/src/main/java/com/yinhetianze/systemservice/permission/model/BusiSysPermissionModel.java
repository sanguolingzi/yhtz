package com.yinhetianze.systemservice.permission.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.Date;

public class BusiSysPermissionModel extends BasicModel{
    private Integer id;

    /**
     * 权限名称
     */
    private String permName;

    private Integer createUser;

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
     * 获取权限名称
     *
     * @return perm_name - 权限名称
     */
    public String getPermName() {
        return permName;
    }

    /**
     * 设置权限名称
     *
     * @param permName 权限名称
     */
    public void setPermName(String permName) {
        this.permName = permName;
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

}