package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;

public class BusiCustomerCollectorModel extends PageModel{
    private Integer id;

    /**
     * 0 商品收藏 1店铺收藏
     */
    private Short cType;

    /**
     * 关联数据id
     */
    private Integer relationId;

    /**
     * 所属用户Id
     */
    private Integer customerId;


    private String name;

    /**
     *  pc   wap
     */
    private String type;

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
     * 获取0 商品收藏 1店铺收藏
     *
     * @return c_type - 0 商品收藏 1店铺收藏
     */
    public Short getcType() {
        return cType;
    }

    /**
     * 设置0 商品收藏 1店铺收藏
     *
     * @param cType 0 商品收藏 1店铺收藏
     */
    public void setcType(Short cType) {
        this.cType = cType;
    }

    /**
     * 获取关联数据id
     *
     * @return relation_id - 关联数据id
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * 设置关联数据id
     *
     * @param relationId 关联数据id
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}