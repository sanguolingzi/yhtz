package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;

import java.util.List;

public interface CustomerReceiveaddressInfoService
{
    BusiCustomerReceiveaddressPojo selectOne(BusiCustomerReceiveaddressPojo record);

    List<BusiCustomerReceiveaddressPojo> selectByCustomerId(Integer customerId);
}