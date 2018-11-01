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
     * 金额排序
     */
    private Integer totalAmountSort;

    /**
     * 时间排序
     * @return
     */
    private Integer createTimeSort;

    /**
     *总订单号
     * @return
     */
    private String totalOrderNo;

    /**
     * 支付设备(1WAP 2PC)
     * @return
     */
    private Integer payDevice;

    /**
     * 是否为微信浏览器（0不是 1是）
     */
    private Integer isWXDevice;

    /**
     * 时间排序
     * @return
     */
    private Integer completeTimeSort;

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
     * 是否可结算（0不可结算 1可结算）
     */
    private Integer isSettlement;

    /**
     * 是否为商家（0不是 1是）
     */
    private Integer isShop;

    /**
     * 订单详情ID
     */
    private Integer orderDetailId;

    /**
     * 结算单ID
     * @return
     */
    private Integer settlementId;

    /**
     * openId类型
     * @return
     */
    private Short idType;

    /**
     * 订单类型
     * @return
     */
    private String isGameOrder;

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

    public Integer getTotalAmountSort() {
        return totalAmountSort;
    }

    public void setTotalAmountSort(Integer totalAmountSort) {
        this.totalAmountSort = totalAmountSort;
    }

    public Integer getCreateTimeSort() {
        return createTimeSort;
    }

    public void setCreateTimeSort(Integer createTimeSort) {
        this.createTimeSort = createTimeSort;
    }

    public String getTotalOrderNo() {
        return totalOrderNo;
    }

    public void setTotalOrderNo(String totalOrderNo) {
        this.totalOrderNo = totalOrderNo;
    }

    public Integer getPayDevice() {
        return payDevice;
    }

    public void setPayDevice(Integer payDevice) {
        this.payDevice = payDevice;
    }

    public Integer getIsWXDevice() {
        return isWXDevice;
    }

    public void setIsWXDevice(Integer isWXDevice) {
        this.isWXDevice = isWXDevice;
    }

    public Integer getCompleteTimeSort() {
        return completeTimeSort;
    }

    public void setCompleteTimeSort(Integer completeTimeSort) {
        this.completeTimeSort = completeTimeSort;
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

    public Integer getIsSettlement() {
        return isSettlement;
    }

    public void setIsSettlement(Integer isSettlement) {
        this.isSettlement = isSettlement;
    }

    public Integer getIsShop() {
        return isShop;
    }

    public void setIsShop(Integer isShop) {
        this.isShop = isShop;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getSettlementId(){
        return settlementId;
    }

    public void setSettlementId(Integer settlementId){
        this.settlementId = settlementId;
    }

    public Short getIdType() {
        return idType;
    }

    public void setIdType(Short idType) {
        this.idType = idType;
    }

    public String getIsGameOrder() {
        return isGameOrder;
    }

    public void setIsGameOrder(String isGameOrder) {
        this.isGameOrder = isGameOrder;
    }
}
