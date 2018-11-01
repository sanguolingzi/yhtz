package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.pojo.customer.CustomerEarningsPojo;

import java.util.List;

public interface CustomerEarningsBusiService
{
    int addInfo(CustomerEarningsPojo customerEarningsPojo);

    int addInfoBatch(List<CustomerEarningsPojo> list);

}