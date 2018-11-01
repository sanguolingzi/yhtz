package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.util.Date;

public class ShopGuessModel extends PageModel {

    private Integer id;

    /**
     * 店铺id
     */

    private Integer shopId;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 0 正常 1 已删除
     */

    private Short delFlag;


    private Date createTime;


    private Date updateTime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }


    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }


    public Short getSort() {
        return sort;
    }


    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Short getDelFlag() {
        return delFlag;
    }


    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
