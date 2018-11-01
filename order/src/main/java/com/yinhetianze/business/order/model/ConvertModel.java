package com.yinhetianze.business.order.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;

public class ConvertModel extends BasicModel {

    /**
     * 兑换商品Id
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
}
