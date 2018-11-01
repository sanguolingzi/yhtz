package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 消费情况
 */
public class BusiCustomerConsumptionModel extends PageModel {

    private String flowNumber;

    private Short flowType;
    /**
     * 创建时间
     */
    private String createTime;

    private String content;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFlowNumber() {
        return flowNumber;
    }

    public void setFlowNumber(String flowNumber) {
        this.flowNumber = flowNumber;
    }

    public Short getFlowType() {
        return flowType;
    }

    public void setFlowType(Short flowType) {
        this.flowType = flowType;
    }
}