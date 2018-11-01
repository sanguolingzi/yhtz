package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;

/**
 *  个人中心 今日摘星 页面数据实体
 */
public class BusiCustomerStarCoinInfoModel extends BasicModel{
    /**
     * 积分
     */
    private BigDecimal integral;

    /**
     * 星币
     */
    private BigDecimal starCoin;


    private Integer customerId;

    /**
     * 当前所在星期 摘星情况  {"1",{"value":"20","curr":1},"2":{{"value":"30","curr":0},"3":{{"value":"10","curr":1}}
     */
    private String jsonObject;

    /**
     * 获取积分
     *
     * @return integral - 积分
     */
    public BigDecimal getIntegral() {
        return integral;
    }

    /**
     * 设置积分
     *
     * @param integral 积分
     */
    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }


    public BigDecimal getStarCoin() {
        return starCoin;
    }

    public void setStarCoin(BigDecimal starCoin) {
        this.starCoin = starCoin;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }
}