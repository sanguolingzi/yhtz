package com.yinhetianze.business.evaluate.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.List;

public class EvaluateDto extends BasicModel {

    private Integer orderId;

    private List<EvaluateModel> evaluateList;

    private Integer customerId;

    private Integer addEvaluate;

    private String orderNo;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getAddEvaluate() {
        return addEvaluate;
    }

    public void setAddEvaluate(Integer addEvaluate) {
        this.addEvaluate = addEvaluate;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<EvaluateModel> getEvaluateList() {
        return evaluateList;
    }

    public void setEvaluateList(List<EvaluateModel> evaluateList) {
        this.evaluateList = evaluateList;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
