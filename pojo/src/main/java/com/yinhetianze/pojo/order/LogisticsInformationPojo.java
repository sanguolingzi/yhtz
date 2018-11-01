package com.yinhetianze.pojo.order;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_logistics_information")
public class LogisticsInformationPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "orders_id")
    private Integer ordersId;

    /**
     * 快递公司
     */
    @Column(name = "express_company")
    private String expressCompany;

    /**
     * 快递公司编码
     */
    @Column(name = "express_code")
    private String expressCode;

    /**
     * 物流单号
     */
    @Column(name = "logistice_code")
    private String logisticeCode;

    /**
     * 收件人
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 收件人手机
     */
    @Column(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 收件省
     */
    @Column(name = "receiver_province")
    private String receiverProvince;

    /**
     * 收件市
     */
    @Column(name = "receiver_city")
    private String receiverCity;

    /**
     * 收件区/县
     */
    @Column(name = "receiver_area")
    private String receiverArea;

    /**
     * 收件人详细地址
     */
    @Column(name = "receiver_address")
    private String receiverAddress;

    /**
     * 发件人
     */
    @Column(name = "sender_name")
    private String senderName;

    /**
     * 发件人手机
     */
    @Column(name = "sender_Mobile")
    private String senderMobile;

    /**
     * 发件省
     */
    @Column(name = "sender_province")
    private String senderProvince;

    /**
     * 发件市
     */
    @Column(name = "sender_city")
    private String senderCity;

    /**
     * 发件区/县
     */
    @Column(name = "sender_area")
    private String senderArea;

    /**
     * 发件人详细地址
     */
    @Column(name = "sender_address")
    private String senderAddress;

    /**
     * 订阅时间
     */
    @Column(name = "subscription_time")
    private Date subscriptionTime;

    /**
     * true:订阅成功 false:订阅失败
     */
    @Column(name = "is_subscription")
    private String isSubscription;

    /**
     * 订阅失败原因
     */
    @Column(name = "subscription_reason")
    private String subscriptionReason;

    /**
     * 预计到货时间
     */
    @Column(name = "estimated_delivery_time")
    private Date estimatedDeliveryTime;

    /**
     * 推送时间
     */
    @Column(name = "push_time")
    private Date pushTime;

    /**
     * 推送物流信息
     */
    @Column(name = "push_logistics")
    private String pushLogistics;

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
     * 获取订单id
     *
     * @return orders_id - 订单id
     */
    public Integer getOrdersId() {
        return ordersId;
    }

    /**
     * 设置订单id
     *
     * @param ordersId 订单id
     */
    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    /**
     * 获取快递公司
     *
     * @return express_company - 快递公司
     */
    public String getExpressCompany() {
        return expressCompany;
    }

    /**
     * 设置快递公司
     *
     * @param expressCompany 快递公司
     */
    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    /**
     * 获取快递公司编码
     *
     * @return express_code - 快递公司编码
     */
    public String getExpressCode() {
        return expressCode;
    }

    /**
     * 设置快递公司编码
     *
     * @param expressCode 快递公司编码
     */
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    /**
     * 获取物流单号
     *
     * @return logistice_code - 物流单号
     */
    public String getLogisticeCode() {
        return logisticeCode;
    }

    /**
     * 设置物流单号
     *
     * @param logisticeCode 物流单号
     */
    public void setLogisticeCode(String logisticeCode) {
        this.logisticeCode = logisticeCode;
    }

    /**
     * 获取收件人
     *
     * @return receiver_name - 收件人
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * 设置收件人
     *
     * @param receiverName 收件人
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * 获取收件人手机
     *
     * @return receiver_mobile - 收件人手机
     */
    public String getReceiverMobile() {
        return receiverMobile;
    }

    /**
     * 设置收件人手机
     *
     * @param receiverMobile 收件人手机
     */
    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    /**
     * 获取收件省
     *
     * @return receiver_province - 收件省
     */
    public String getReceiverProvince() {
        return receiverProvince;
    }

    /**
     * 设置收件省
     *
     * @param receiverProvince 收件省
     */
    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    /**
     * 获取收件市
     *
     * @return receiver_city - 收件市
     */
    public String getReceiverCity() {
        return receiverCity;
    }

    /**
     * 设置收件市
     *
     * @param receiverCity 收件市
     */
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    /**
     * 获取收件区/县
     *
     * @return receiver_area - 收件区/县
     */
    public String getReceiverArea() {
        return receiverArea;
    }

    /**
     * 设置收件区/县
     *
     * @param receiverArea 收件区/县
     */
    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    /**
     * 获取收件人详细地址
     *
     * @return receiver_address - 收件人详细地址
     */
    public String getReceiverAddress() {
        return receiverAddress;
    }

    /**
     * 设置收件人详细地址
     *
     * @param receiverAddress 收件人详细地址
     */
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    /**
     * 获取发件人
     *
     * @return sender_name - 发件人
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * 设置发件人
     *
     * @param senderName 发件人
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * 获取发件人手机
     *
     * @return sender_Mobile - 发件人手机
     */
    public String getSenderMobile() {
        return senderMobile;
    }

    /**
     * 设置发件人手机
     *
     * @param senderMobile 发件人手机
     */
    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    /**
     * 获取发件省
     *
     * @return sender_province - 发件省
     */
    public String getSenderProvince() {
        return senderProvince;
    }

    /**
     * 设置发件省
     *
     * @param senderProvince 发件省
     */
    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    /**
     * 获取发件市
     *
     * @return sender_city - 发件市
     */
    public String getSenderCity() {
        return senderCity;
    }

    /**
     * 设置发件市
     *
     * @param senderCity 发件市
     */
    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    /**
     * 获取发件区/县
     *
     * @return sender_area - 发件区/县
     */
    public String getSenderArea() {
        return senderArea;
    }

    /**
     * 设置发件区/县
     *
     * @param senderArea 发件区/县
     */
    public void setSenderArea(String senderArea) {
        this.senderArea = senderArea;
    }

    /**
     * 获取发件人详细地址
     *
     * @return sender_address - 发件人详细地址
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * 设置发件人详细地址
     *
     * @param senderAddress 发件人详细地址
     */
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    /**
     * 获取订阅时间
     *
     * @return subscription_time - 订阅时间
     */
    public Date getSubscriptionTime() {
        return subscriptionTime;
    }

    /**
     * 设置订阅时间
     *
     * @param subscriptionTime 订阅时间
     */
    public void setSubscriptionTime(Date subscriptionTime) {
        this.subscriptionTime = subscriptionTime;
    }

    /**
     * 获取true:订阅成功 false:订阅失败
     *
     * @return is_subscription - true:订阅成功 false:订阅失败
     */
    public String getIsSubscription() {
        return isSubscription;
    }

    /**
     * 设置true:订阅成功 false:订阅失败
     *
     * @param isSubscription true:订阅成功 false:订阅失败
     */
    public void setIsSubscription(String isSubscription) {
        this.isSubscription = isSubscription;
    }

    /**
     * 获取订阅失败原因
     *
     * @return subscription_reason - 订阅失败原因
     */
    public String getSubscriptionReason() {
        return subscriptionReason;
    }

    /**
     * 设置订阅失败原因
     *
     * @param subscriptionReason 订阅失败原因
     */
    public void setSubscriptionReason(String subscriptionReason) {
        this.subscriptionReason = subscriptionReason;
    }

    /**
     * 获取预计到货时间
     *
     * @return estimated_delivery_time - 预计到货时间
     */
    public Date getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    /**
     * 设置预计到货时间
     *
     * @param estimatedDeliveryTime 预计到货时间
     */
    public void setEstimatedDeliveryTime(Date estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    /**
     * 获取推送时间
     *
     * @return push_time - 推送时间
     */
    public Date getPushTime() {
        return pushTime;
    }

    /**
     * 设置推送时间
     *
     * @param pushTime 推送时间
     */
    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    /**
     * 获取推送物流信息
     *
     * @return push_logistics - 推送物流信息
     */
    public String getPushLogistics() {
        return pushLogistics;
    }

    /**
     * 设置推送物流信息
     *
     * @param pushLogistics 推送物流信息
     */
    public void setPushLogistics(String pushLogistics) {
        this.pushLogistics = pushLogistics;
    }
}