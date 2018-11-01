package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.Date;

public class ProductFresherRewardModel extends BasicModel{
    private Integer id;

    /**
     * 用户id
     */
    private Integer custId;

    /**
     * 是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     */
    private Short status;

    /**
     * 兑换的商品ID
     */
    private Integer prodId;

    /**
     * U币抵扣量
     */
    private BigDecimal exchangeUPrice;

    /**
     * 兑换时间
     */
    private Date exchangeTime;

    /**
     * 创建时间
     */
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
     * 获取用户id
     *
     * @return cust_id - 用户id
     */
    public Integer getCustId() {
        return custId;
    }

    /**
     * 设置用户id
     *
     * @param custId 用户id
     */
    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    /**
     * 获取是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     *
     * @return STATUS - 是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     *
     * @param status 是否兑换了奖励，0未兑换，1已兑换，默认0未兑换
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 获取兑换的商品ID
     *
     * @return prod_id - 兑换的商品ID
     */
    public Integer getProdId() {
        return prodId;
    }

    /**
     * 设置兑换的商品ID
     *
     * @param prodId 兑换的商品ID
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    /**
     * 获取U币抵扣量
     *
     * @return exchange_u_price - U币抵扣量
     */
    public BigDecimal getExchangeUPrice() {
        return exchangeUPrice;
    }

    /**
     * 设置U币抵扣量
     *
     * @param exchangeUPrice U币抵扣量
     */
    public void setExchangeUPrice(BigDecimal exchangeUPrice) {
        this.exchangeUPrice = exchangeUPrice;
    }

    /**
     * 获取兑换时间
     *
     * @return exchange_time - 兑换时间
     */
    public Date getExchangeTime() {
        return exchangeTime;
    }

    /**
     * 设置兑换时间
     *
     * @param exchangeTime 兑换时间
     */
    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
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
}