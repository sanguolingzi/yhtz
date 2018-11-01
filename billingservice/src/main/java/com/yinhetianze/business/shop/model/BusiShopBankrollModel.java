package com.yinhetianze.business.shop.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.Date;

public class BusiShopBankrollModel extends BasicModel{
    private Integer id;

    private BigDecimal goodsAmount;

    private Integer shopId;

    private Short delFlag;

    private Date createTime;

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
     * @return goods_amount
     */
    public BigDecimal getGoodsAmount() {
        return goodsAmount;
    }

    /**
     * @param goodsAmount
     */
    public void setGoodsAmount(BigDecimal goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    /**
     * @return shop_id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * @param shopId
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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
}