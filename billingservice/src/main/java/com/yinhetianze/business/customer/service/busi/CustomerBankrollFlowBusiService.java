package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;

import java.util.Map;

public interface CustomerBankrollFlowBusiService {

    int insert(BusiCustomerBankrollFlowPojo record);

    int insertSelective(BusiCustomerBankrollFlowPojo record);

    int updateByPrimaryKeySelective(BusiCustomerBankrollFlowPojo record);

    int updateByPrimaryKey(BusiCustomerBankrollFlowPojo record);

    int updateGameIdFlowToCustomer(Map<String,Object> paraMap);

}