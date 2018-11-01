package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;

/**
 *   个人中心-奖励金页面
 */
public class BusiRecommendAmountInfoModel extends BasicModel {


    private Integer customerId;
    /**
     * 奖励金
     */
    private BigDecimal amount;


    /**
     * 推荐人-被推荐关系列表
     */
    //private PageInfo<BusiCustomerRecommendRelationModel> busiCustomerRecommendRelationModel;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}