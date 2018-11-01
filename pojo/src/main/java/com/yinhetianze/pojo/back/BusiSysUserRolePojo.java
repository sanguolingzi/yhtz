package com.yinhetianze.pojo.back;

import javax.persistence.*;

@Table(name = "busi_sys_userrole_relation")
public class BusiSysUserRolePojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色Id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 用户id
     */
    @Column(name = "optor_id")
    private Integer optorId;

    @Column(name = "del_flag")
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
     * 获取角色Id
     *
     * @return role_id - 角色Id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色Id
     *
     * @param roleId 角色Id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取用户id
     *
     * @return optor_id - 用户id
     */
    public Integer getOptorId() {
        return optorId;
    }

    /**
     * 设置用户id
     *
     * @param optorId 用户id
     */
    public void setOptorId(Integer optorId) {
        this.optorId = optorId;
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
}