package com.yinhetianze.pojo.product;

import java.util.Date;
import javax.persistence.*;
@Table(name = "busi_product_guess")
public class ProductGuessPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联商品id
     */
    @Column(name = "prod_id")
    private Integer prodId;

    @Column(name = "create_user")
    private Short createUser;

    @Column(name = "create_time")
    private Date  createTime;

    @Column(name = "update_user")
    private Short updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;
    /**
     * 排序号
     */
    private Short sort;
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
     * 获取关联商品id
     *
     * @return prod_id - 关联商品id
     */
    public Integer getProdId() {
        return prodId;
    }

    /**
     * 设置关联商品id
     *
     * @param prodId 关联商品id
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    /**
     * @return create_user
     */
    public Short getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Short createUser) {
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
    public Short getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(Short updateUser) {
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

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }
}