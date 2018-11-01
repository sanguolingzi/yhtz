package com.yinhetianze.business.order.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;

public class ActivityOrderModel extends BasicModel {

    /**
     * 活动商品Id
     */
    private Integer id;

    /**
     * 收货地址ID
     */
    private Integer receiveaddressId;

    /**
     * 下单来源(1 PC 2WAP 3 APP  4 微信)
     */
    private Integer orderSourse;

    /**
     * 使用U币数量
     */
    private BigDecimal uPrice;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     *支付金额
     */
    private BigDecimal payAmount;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 商品数量
     */
    private Integer number;

    /**
     * 使用积分
     */
    private BigDecimal payIntegral;

    /**
     * 买家留言
     */
    private String buyerMessage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceiveaddressId() {
        return receiveaddressId;
    }

    public void setReceiveaddressId(Integer receiveaddressId) {
        this.receiveaddressId = receiveaddressId;
    }

    public Integer getOrderSourse() {
        return orderSourse;
    }

    public void setOrderSourse(Integer orderSourse) {
        this.orderSourse = orderSourse;
    }

    public BigDecimal getuPrice() {
        return uPrice;
    }

    public void setuPrice(BigDecimal uPrice) {
        this.uPrice = uPrice;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getPayIntegral() {
        return payIntegral;
    }

    public void setPayIntegral(BigDecimal payIntegral) {
        this.payIntegral = payIntegral;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }
}
