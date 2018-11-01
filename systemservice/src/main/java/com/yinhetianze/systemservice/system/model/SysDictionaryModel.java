package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_sys_dictionary")
public class SysDictionaryModel extends PageModel{
    private Integer id;

    /**
     * 字典名称
     */
    private String dicName;

    /**
     * 字典值
     */
    private String dicValue;

    /**
     * 字典类型
     */
    private String dicType;

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
     * 字典描述
     */
    private String dicDescription;

    /**
     * 0 正常 1 已删除
     */
    private Short delFlag;

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
     * 获取字典名称
     *
     * @return dic_name - 字典名称
     */
    public String getDicName() {
        return dicName;
    }

    /**
     * 设置字典名称
     *
     * @param dicName 字典名称
     */
    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    /**
     * 获取字典值
     *
     * @return dic_value - 字典值
     */
    public String getDicValue() {
        return dicValue;
    }

    /**
     * 设置字典值
     *
     * @param dicValue 字典值
     */
    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    /**
     * 获取字典类型
     *
     * @return dic_type - 字典类型
     */
    public String getDicType() {
        return dicType;
    }

    /**
     * 设置字典类型
     *
     * @param dicType 字典类型
     */
    public void setDicType(String dicType) {
        this.dicType = dicType;
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
     * 获取字典描述
     *
     * @return dic_description - 字典描述
     */
    public String getDicDescription() {
        return dicDescription;
    }

    /**
     * 设置字典描述
     *
     * @param dicDescription 字典描述
     */
    public void setDicDescription(String dicDescription) {
        this.dicDescription = dicDescription;
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