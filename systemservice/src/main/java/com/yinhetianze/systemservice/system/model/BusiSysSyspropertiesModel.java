package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;


public class BusiSysSyspropertiesModel extends PageModel{

    private Integer id;

    /**
     * 参数名称
     */

    private String pName;

    /**
     * 参数值
     */

    private String pValue;


    private Integer createUser;

    /**
     * 创建时间
     */

    private Date createTime;


    private Integer updateUser;

    private Short isWork;

    private Short pModule;

    private String pDescription;

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
     * 获取参数名称
     *
     * @return p_name - 参数名称
     */
    public String getpName() {
        return pName;
    }

    /**
     * 设置参数名称
     *
     * @param pName 参数名称
     */
    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
     * 获取参数值
     *
     * @return p_value - 参数值
     */
    public String getpValue() {
        return pValue;
    }

    /**
     * 设置参数值
     *
     * @param pValue 参数值
     */
    public void setpValue(String pValue) {
        this.pValue = pValue;
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


    public Short getIsWork() {
        return isWork;
    }

    public void setIsWork(Short isWork) {
        this.isWork = isWork;
    }

    public Short getpModule() {
        return pModule;
    }

    public void setpModule(Short pModule) {
        this.pModule = pModule;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }
}