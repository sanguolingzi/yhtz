package com.yinhetianze.business.order.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderModel extends BasicModel {

    /**
     * 用户ID
     */
    private Integer customerId;
    /**
     * 下单来源(1 PC 2WAP 3 APP  4 微信)
     */
    private Integer orderSourse;
    /**
     * 收货地址ID
     */
    private Integer receiveaddressId;
    /**
     * 订单类型(0 线上  1 线下)
     */
    private Integer orderType;
    /**
     * 商品信息集合
     */
    private List<Map<String,Object>> shopList;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 使用积分
     */
    private BigDecimal payIntegral;
    /**
     *支付金额
     */
    private BigDecimal payAmount;
    /**
     * 购物车主键数组(下单后删除购物车信息)
     */
    private Integer[] shopcartIds;

    private Map pro;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderSourse() {
        return orderSourse;
    }

    public void setOrderSourse(Integer orderSourse) {
        this.orderSourse = orderSourse;
    }

    public Integer getReceiveaddressId() {
        return receiveaddressId;
    }

    public void setReceiveaddressId(Integer receiveaddressId) {
        this.receiveaddressId = receiveaddressId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public List<Map<String, Object>> getShopList() {
        return shopList;
    }

    public void setShopList(List<Map<String, Object>> shopList) {
        this.shopList = shopList;
    }

    public BigDecimal getPayIntegral() {
        return payIntegral;
    }

    public void setPayIntegral(BigDecimal payIntegral) {
        this.payIntegral = payIntegral;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer[] getShopcartIds() {
        return shopcartIds;
    }

    public void setShopcartIds(Integer[] shopcartIds) {
        this.shopcartIds = shopcartIds;
    }

    public Map getPro() {
        return pro;
    }

    public void setPro(Map pro) {
        this.pro = pro;
    }
}
