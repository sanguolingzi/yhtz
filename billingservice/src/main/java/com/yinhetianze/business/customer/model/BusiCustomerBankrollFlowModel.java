package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;

public class BusiCustomerBankrollFlowModel extends PageModel {
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
     * 结束时间
     */
    private String endTime;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 流水号
     */
    private String flowNumber;

    /**
     * 流水类别描述(1提现 2 推荐奖励 3余额支付 4获取消费券 5消费券支付 6赠送积分 7摘星消耗积分)
     */
    private Short flowDescription;

    /**
     * 所属账户id
     */
    private Integer bankrollId;

    /**
     * 所属消费者
     */
    private Integer customerId;

    /**
     * 关联数据id
     */
    private Integer relationId;

    private Integer createId;

    private Integer gameId;
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
     * 获取流水号
     *
     * @return flow_number - 流水号
     */
    public String getFlowNumber() {
        return flowNumber;
    }

    /**
     * 设置流水号
     *
     * @param flowNumber 流水号
     */
    public void setFlowNumber(String flowNumber) {
        this.flowNumber = flowNumber;
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

    /**
     * 获取所属账户id
     *
     * @return bankroll_id - 所属账户id
     */
    public Integer getBankrollId() {
        return bankrollId;
    }

    /**
     * 设置所属账户id
     *
     * @param bankrollId 所属账户id
     */
    public void setBankrollId(Integer bankrollId) {
        this.bankrollId = bankrollId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}