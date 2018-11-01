package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.List;

/**
 *  修改账户 (积分、余额) 实体
 */
public class BusiUpdateBankrollModel extends BasicModel {

    /**
     * 消费者id
     */
    private Integer customerId;

    /**
     * 游戏用户Id
     */
    private Integer gameId;
    /**
     * 余额
     */
    private BigDecimal amount;

    /**
     * 余额账户操作类型 0 收入 1 支出
     */
    private Short amountAddOrMinus;
    /**
     * 消费券
     */
    private BigDecimal starCoin;


    /**
     * 消费券账户操作类型 0 收入 1 支出
     */
    private Short starCoinAddOrMinus;

    /**
     * 积分
     */
    private BigDecimal integral;


    /**
     * 积分账户操作类型 0 收入 1 支出
     */
    private Short integralAddOrMinus;

    /**
     * 游戏任务奖励
     */
    private BigDecimal rewardAmount;

    /**
     * 游戏任务奖励账户操作类型 0 收入 1 支出
     */
    private Short rewardAmountAddOrMinus;

    /**
     * 流水相关信息集合
     */
    List<BusiCustomerBankrollFlowModel> list;

    public List<BusiCustomerBankrollFlowModel> getList() {
        return list;
    }

    public void setList(List<BusiCustomerBankrollFlowModel> list) {
        this.list = list;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getStarCoin() {
        return starCoin;
    }

    public void setStarCoin(BigDecimal starCoin) {
        this.starCoin = starCoin;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public Short getAmountAddOrMinus() {
        return amountAddOrMinus;
    }

    public void setAmountAddOrMinus(Short amountAddOrMinus) {
        this.amountAddOrMinus = amountAddOrMinus;
    }

    public Short getStarCoinAddOrMinus() {
        return starCoinAddOrMinus;
    }

    public void setStarCoinAddOrMinus(Short starCoinAddOrMinus) {
        this.starCoinAddOrMinus = starCoinAddOrMinus;
    }

    public Short getIntegralAddOrMinus() {
        return integralAddOrMinus;
    }

    public void setIntegralAddOrMinus(Short integralAddOrMinus) {
        this.integralAddOrMinus = integralAddOrMinus;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Short getRewardAmountAddOrMinus() {
        return rewardAmountAddOrMinus;
    }

    public void setRewardAmountAddOrMinus(Short rewardAmountAddOrMinus) {
        this.rewardAmountAddOrMinus = rewardAmountAddOrMinus;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}