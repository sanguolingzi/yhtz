package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;

public class BusiAmountFlowModel extends PageModel {
    private Integer id;

    /**
     * 1 余额 2 积分 3消费券 4 微信 5 支付宝 6 游戏任务奖励
     */
    private Short flowCategory;

    /**
     * 0 收入 1 支出
     */
    private Short flowType;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 金额
     */
    private BigDecimal amount;


    /**
     * 流水类别描述(1提现 2 推荐奖励 3余额支付 4获取消费券 5消费券支付 6赠送积分 7摘星消耗积分)
     */
    private Short flowDescription;

    /**
     * 关联金额
     */
    private BigDecimal relationAmount;

    /**
     *  订单类型
     */
    private Short isGameOrder;

    /**
     * 操作用户头像 默认用户头像 其次 关联微信头像 否则为null
     */
    private String photoUrl;
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
     * 获取1 提现
     *
     * @return flow_category - 1 提现
     */
    public Short getFlowCategory() {
        return flowCategory;
    }

    /**
     * 设置1 提现
     *
     * @param flowCategory 1 提现
     */
    public void setFlowCategory(Short flowCategory) {
        this.flowCategory = flowCategory;
    }

    /**
     * 获取0 收入 1 支出
     *
     * @return flow_type - 0 收入 1 支出
     */
    public Short getFlowType() {
        return flowType;
    }

    /**
     * 设置0 收入 1 支出
     *
     * @param flowType 0 收入 1 支出
     */
    public void setFlowType(Short flowType) {
        this.flowType = flowType;
    }

    /**
     * 获取金额
     *
     * @return amount - 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * @return flow_description
     */
    public Short getFlowDescription() {
        return flowDescription;
    }

    /**
     * @param flowDescription
     */
    public void setFlowDescription(Short flowDescription) {
        this.flowDescription = flowDescription;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getRelationAmount() {
        return relationAmount;
    }

    public void setRelationAmount(BigDecimal relationAmount) {
        this.relationAmount = relationAmount;
    }

    public Short getIsGameOrder() {
        return isGameOrder;
    }

    public void setIsGameOrder(Short isGameOrder) {
        this.isGameOrder = isGameOrder;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}