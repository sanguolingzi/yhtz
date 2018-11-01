package com.yinhetianze.business.product.model;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httprequest.PageModel;
import javax.persistence.Column;
import java.util.Date;

public class ProductGuessModel  extends PageModel {
    
    private Integer id;

    /**
     * 关联商品id
     */
    private Integer prodId;

    private Short createUser;

    private Date createTime;

    private Short updateUser;

    private Date updateTime;

    private String prodName;

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    /**
     * 排序号
     */
    private Short sort;
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
