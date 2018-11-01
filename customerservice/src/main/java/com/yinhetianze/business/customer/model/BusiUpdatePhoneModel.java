package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

/**
 *   消费者修改手机号
 */
public class BusiUpdatePhoneModel extends BasicModel {

    private Integer id;
    /**
     * 联系电话
     */
    private String phone;

    /**
     *  新手机号
     */
    private String newPhone;

    /**
     *
     */
    private String smsCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}