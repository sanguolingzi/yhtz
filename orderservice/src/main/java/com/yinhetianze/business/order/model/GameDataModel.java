package com.yinhetianze.business.order.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 游戏数据返回业务处理model
 */
public class GameDataModel extends BasicModel{
    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 商品总价
     */
    private BigDecimal proAmount;
    /**
     *购买数量
     */
    private Integer number;
    /**
     * 付款价格
     */
    private String payAmount;

    /**
     * 总订单号
     * @return
     */
    private String totalOrderNo;

    /**
     * 订单编号
     * @return
     */
    private String ordersNo;

    /**
     * 平台用户ID
     * @return
     */
    private Integer customerId;

    /**
     * 收货地址
     * @return
     */
    private String address;

    /**
     * 收货人
     * @return
     */
    private String receiver;

    /**
     * 赠送消费券数量
     * @return
     */
    private BigDecimal  giveIntegral;

    /**
     * 订单总价
     * @return
     */
    private BigDecimal totalAmount;

    /**
     * 订单是否赠送积分(0未赠送，1已赠送)
     */
    private Integer isGiveIntegral;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 抵扣积分
     */
    private BigDecimal payIntegral;

    /**
     * 第三方支付交易流水号
     */
    private String  dealNo;

    /**
     * 第三方交易结果
     */
    private String transResult;

    /**
     * 交易时间20180523101600
     */
    private Date tradeTime;

    public Integer getOrderStatus(){
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus){
        this.orderStatus = orderStatus;
    }

    public BigDecimal getProAmount(){
        return proAmount;
    }

    public void setProAmount(BigDecimal proAmount){
        this.proAmount = proAmount;
    }

    public Integer getNumber(){
        return number;
    }

    public void setNumber(Integer number){
        this.number = number;
    }

    public String getPayAmount(){
        return payAmount;
    }

    public void setPayAmount(String payAmount){
        this.payAmount = payAmount;
    }

    public String getTotalOrderNo(){
        return totalOrderNo;
    }

    public void setTotalOrderNo(String totalOrderNo){
        this.totalOrderNo = totalOrderNo;
    }

    public String getOrdersNo(){
        return ordersNo;
    }

    public void setOrdersNo(String ordersNo){
        this.ordersNo = ordersNo;
    }

    public Integer getCustomerId(){
        return customerId;
    }

    public void setCustomerId(Integer customerId){
        this.customerId = customerId;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getReceiver(){
        return receiver;
    }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public BigDecimal getGiveIntegral(){
        return giveIntegral;
    }

    public void setGiveIntegral(BigDecimal giveIntegral){
        this.giveIntegral = giveIntegral;
    }

    public BigDecimal getTotalAmount(){
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount){
        this.totalAmount = totalAmount;
    }

    public Integer getIsGiveIntegral(){
        return isGiveIntegral;
    }

    public void setIsGiveIntegral(Integer isGiveIntegral){
        this.isGiveIntegral = isGiveIntegral;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public BigDecimal getPayIntegral(){
        return payIntegral;
    }

    public void setPayIntegral(BigDecimal payIntegral){
        this.payIntegral = payIntegral;
    }

    public String getDealNo(){
        return dealNo;
    }

    public void setDealNo(String dealNo){
        this.dealNo = dealNo;
    }

    public String getTransResult(){
        return transResult;
    }

    public void setTransResult(String transResult){
        this.transResult = transResult;
    }

    public Date getTradeTime(){
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime){
        this.tradeTime = tradeTime;
    }
}