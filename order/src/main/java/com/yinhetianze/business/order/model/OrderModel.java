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

    private String gameId;

    /**
     * 开发票类型（0没有发票 1个人 2公司）
     */
    private Integer taxType;

    /**
     * 公司开票的税号
     */
    private String taxNo;

    /**
     *开票公司名称
     * @return
     */
    private String taxCompany;

    /**
     * 发票类型（1纸质 2电子）
     */
    private Integer receiptType;

    /**
     * 是否以分享价下单(1是)
     */
    private Integer isShare;

    /**
     * 买家留言
     */
    private String buyerMessage;

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

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getTaxCompany() {
        return taxCompany;
    }

    public void setTaxCompany(String taxCompany) {
        this.taxCompany = taxCompany;
    }

    public Integer getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(Integer receiptType) {
        this.receiptType = receiptType;
    }

    public Integer getIsShare() {
        return isShare;
    }

    public void setIsShare(Integer isShare) {
        this.isShare = isShare;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }
}
