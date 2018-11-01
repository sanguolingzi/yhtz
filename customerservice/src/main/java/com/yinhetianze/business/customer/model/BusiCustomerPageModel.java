package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

public class BusiCustomerPageModel extends PageModel {
    private String id;
    /**
     * 联系电话
     */
    private String phone;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}