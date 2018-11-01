package com.yinhetianze.business.evaluate.model;

import com.yinhetianze.core.business.httprequest.PageModel;

public class EvaluateModel extends PageModel {

    private Integer evaluateId;

    private Integer orderId;

    private Integer productId;

    private String productSuk;

    private String productName;

    private String productTitle;

    private String productImg;

    private Integer shopId;

    private String productSpec;

    private String content;

    private String evaluateImg;

    private Integer productStar;

    private Integer serviceStar;

    private Integer logisticsStar;

    private Integer evaluateUser;

    private Integer addEvaluate;

    private String answer;

    private Integer isShow;

    private Integer[] evaluateIds;

    private Integer createTimeSort;

    private String orderNo;

    private Integer isShop;

    private String createTime;

    private String answerTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Integer evaluateId) {
        this.evaluateId = evaluateId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductSuk() {
        return productSuk;
    }

    public void setProductSuk(String productSuk) {
        this.productSuk = productSuk;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEvaluateImg() {
        return evaluateImg;
    }

    public void setEvaluateImg(String evaluateImg) {
        this.evaluateImg = evaluateImg;
    }

    public Integer getProductStar() {
        return productStar;
    }

    public void setProductStar(Integer productStar) {
        this.productStar = productStar;
    }

    public Integer getServiceStar() {
        return serviceStar;
    }

    public void setServiceStar(Integer serviceStar) {
        this.serviceStar = serviceStar;
    }

    public Integer getLogisticsStar() {
        return logisticsStar;
    }

    public void setLogisticsStar(Integer logisticsStar) {
        this.logisticsStar = logisticsStar;
    }

    public Integer getEvaluateUser() {
        return evaluateUser;
    }

    public void setEvaluateUser(Integer evaluateUser) {
        this.evaluateUser = evaluateUser;
    }

    public Integer getAddEvaluate() {
        return addEvaluate;
    }

    public void setAddEvaluate(Integer addEvaluate) {
        this.addEvaluate = addEvaluate;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer[] getEvaluateIds() {
        return evaluateIds;
    }

    public void setEvaluateIds(Integer[] evaluateIds) {
        this.evaluateIds = evaluateIds;
    }

    public Integer getCreateTimeSort() {
        return createTimeSort;
    }

    public void setCreateTimeSort(Integer createTimeSort) {
        this.createTimeSort = createTimeSort;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getIsShop() {
        return isShop;
    }

    public void setIsShop(Integer isShop) {
        this.isShop = isShop;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }
}
