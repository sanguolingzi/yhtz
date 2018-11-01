package com.yinhetianze.business.order.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;

public class OneYuanModel extends BasicModel {

    /**
     * 外部主键ID
     */
    private Integer outsideId;

    /**
     * 类型（1一元专区 2礼包专区）
     */
    private Short type;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     *支付金额
     */
    private BigDecimal payAmount;

    /**
     * 收货地址ID
     */
    private Integer receiveaddressId;

    /**
     * 下单来源(1 PC 2WAP 3 APP  4 微信)
     */
    private Integer orderSourse;

    /**
     * 房卡数量
     */
    private Integer roomCardPic;

    /**
     * 买家留言
     */
    private String buyerMessage;

    //商品Id
    private Integer prodId;

    public Integer getOutsideId() {
        return outsideId;
    }

    public void setOutsideId(Integer outsideId) {
        this.outsideId = outsideId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
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

    public Integer getRoomCardPic() {
        return roomCardPic;
    }

    public void setRoomCardPic(Integer roomCardPic) {
        this.roomCardPic = roomCardPic;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }
}
