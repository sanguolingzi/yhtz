package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;

import java.util.List;

public interface CustomerWechatInfoService
{
    BusiCustomerWechatPojo select(BusiCustomerWechatPojo busiCustomerWechatPojo);

    BusiCustomerWechatPojo selectByCustomerCode(String customerCode);

    BusiCustomerWechatPojo selectByOpenId(String OpenId);

    BusiCustomerWechatPojo selectByCustomerId(Integer customerId,Short idType);

    List<BusiCustomerWechatPojo> getOneCustomer(BusiCustomerWechatPojo busiCustomerWechatPojo);
}