package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

/**
 *   消费者 微信
 */
public class BusiWechatModel extends BasicModel {

    /**
     * 微信授权 后台生成code
     */
    private String customerCode;

    /**
     * 用户Id
     */
    private Integer customerId;

    private String code;

    private Short idType;

    private String redirectUrl;

    private String url;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getIdType() {
        return idType;
    }

    public void setIdType(Short idType) {
        this.idType = idType;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}