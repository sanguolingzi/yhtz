package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

/**
 *   消费者修改密码  model
 */
public class BusiUpdatePasswordModel extends BasicModel {


    /**
     * 用户id
     */
    private Integer customerId;
    /**
     * 原登录密码
     */
    private String loginPassword;

    /**
     * 原支付密码
     */
    private String payPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认新密码
     */
    private String confirmPassword;

    /**
     * 短信验证码
     */
    private String smsCode;


    /**
     * 手机号码 忘记密码时候使用
     */
    private String phone;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}