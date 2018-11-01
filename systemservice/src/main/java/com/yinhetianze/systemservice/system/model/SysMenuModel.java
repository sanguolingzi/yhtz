package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;


public class SysMenuModel extends PageModel{

    private Integer id;

    /**
     * 菜单名称
     */

    private String menuName;

    /**
     * 菜单链接
     */

    private String menuLink;

    /**
     * 父菜单
     */

    private Integer parentId;

    private String pMenuName;

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
     *  0 正常 1 失效
     */

    private Short isWork;

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
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取菜单链接
     *
     * @return menu_link - 菜单链接
     */
    public String getMenuLink() {
        return menuLink;
    }

    /**
     * 设置菜单链接
     *
     * @param menuLink 菜单链接
     */
    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    /**
     * 获取父菜单
     *
     * @return parent_id - 父菜单
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单
     *
     * @param parentId 父菜单
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
     * 获取 0 正常 1 失效
     *
     * @return is_work -  0 正常 1 失效
     */
    public Short getIsWork() {
        return isWork;
    }

    /**
     * 设置 0 正常 1 失效
     *
     * @param isWork  0 正常 1 失效
     */
    public void setIsWork(Short isWork) {
        this.isWork = isWork;
    }

    public String getpMenuName() {
        return pMenuName;
    }

    public void setpMenuName(String pMenuName) {
        this.pMenuName = pMenuName;
    }
}