package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import javax.persistence.*;
import java.util.Date;


public class ShopCategoryModel extends BasicModel{

    private Integer id;

    /**
     * 店铺Id
     */

    private Integer shopId;

    /**
     * 分类名称
     */

    private String cateName;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 修改时间
     */

    private Date updateTime;


    /**
     * 修改店内分类用
     */
    private String jsonString;

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
     * 获取店铺Id
     *
     * @return shop_id - 店铺Id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺Id
     *
     * @param shopId 店铺Id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取分类名称
     *
     * @return cate_name - 分类名称
     */
    public String getCateName() {
        return cateName;
    }

    /**
     * 设置分类名称
     *
     * @param cateName 分类名称
     */
    public void setCateName(String cateName) {
        this.cateName = cateName;
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

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}