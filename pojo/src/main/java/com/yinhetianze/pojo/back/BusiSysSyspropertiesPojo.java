package com.yinhetianze.pojo.back;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_sys_sysproperties")
public class BusiSysSyspropertiesPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 参数名称
     */
    @Column(name = "p_name")
    private String pName;

    /**
     * 参数值
     */
    @Column(name = "p_value")
    private String pValue;

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

    @Column(name = "del_flag")
    private Short delFlag;

    @Column(name = "is_work")
    private Short isWork;

    @Column(name = "p_module")
    private Short pModule;

    @Column(name = "p_description")
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
     * @return del_flag
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
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