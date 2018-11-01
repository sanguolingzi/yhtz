package com.yinhetianze.business.order.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;

public class OrderDto extends PageModel {

    private Integer customerId;

    private Integer shopId;

    private String orderStatus;

    private Integer orderId;

    private String payPassword;

    private Integer payType;

    private BigDecimal payAmount;

    private BigDecimal payIntegral;

    private Integer auditId;

    private String ordersNo;

    private String beginTime;

    private String endTime;
    /**
     * 操作人ID
     */
    private Integer userId;

    /**
     * 快递单号
     */
    private String expressNo;
    /**
     * 快递公司名称
     */
    private String companyName;
    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 审核不通过原因
     */
    private String reason;
    /**
     * 用于多个订单支付
     */
    private Integer[] orderIds;

    private String code;

    /**
     * 商家审核退款订单（1同意 2拒绝）
     */
    private Integer refundStatus;

    /**
     * 渠道编码 (第三方接口签名加密用)
     */
    private String channelCode;
    /**
     *渠道秘钥(第三方接口签名加密用)
     */
    private String channelSecret;
    /**
     * 签名(第三方接口签名加密用)
     */
    private String sign;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getOrdersNo() {
        return ordersNo;
    }

    public void setOrdersNo(String ordersNo) {
        this.ordersNo = ordersNo;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer[] getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(Integer[] orderIds) {
        this.orderIds = orderIds;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayIntegral() {
        return payIntegral;
    }

    public void setPayIntegral(BigDecimal payIntegral) {
        this.payIntegral = payIntegral;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getChannelCode(){
        return channelCode;
    }

    public void setChannelCode(String channelCode){
        this.channelCode = channelCode;
    }

    public String getChannelSecret(){
        return channelSecret;
    }

    public void setChannelSecret(String channelSecret){
        this.channelSecret = channelSecret;
    }

    public String getSign(){
        return sign;
    }

    public void setSign(String sign){
        this.sign = sign;
    }

}
