package com.yinhetianze.business.customer.model;

import java.math.BigDecimal;

public class BusiCustomerOrderModel extends BusiCustomerModel {

    /**
     * 订单数量
     */
    private String orderCount;

    /**
     * 订单总金额
     */
    private BigDecimal orderPrice;

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}